module.exports = function follow(api, rootPath, relArray) {
  const root = api({
    method: 'GET',
    path: rootPath
  });

  return relArray.reduce(function (root, arrayItem) {
    const rel = typeof arrayItem === 'string' ? arrayItem : arrayItem.rel;
    return traverseNext(root, rel, arrayItem);
  }, root);

  function traverseNext(root, rel, arrayItem) {
    return root.then(function (response) {
      if (hasEmbeddedRel(response.entity, rel)) {
        console.log("return embedded rel " + rel);
        return response.entity._embedded[rel];
      }

      if (!response.entity._links) {
        console.log("return empty response");
        return [];
      }

      if (typeof arrayItem === 'string') {
        console.log("invoking api with path: " + response.entity._links[rel].href);
        return api({
          method: 'GET',
          path: response.entity._links[rel].href
        });
      } else {
        console.log("invoking api with path: %s and params: %o", response.entity._links[rel].href, arrayItem.params);
        return api({
          method: 'GET',
          path: response.entity._links[rel].href,
          params: arrayItem.params
        });
      }
    });
  }

  function hasEmbeddedRel(entity, rel) {
    return entity._embedded && entity._embedded.hasOwnProperty(rel);
  }
};