# ci/tasks/package.sh

#!/bin/bash

echo $PWD
cd source-code
echo $PWD
ls -la
./mvnw package

