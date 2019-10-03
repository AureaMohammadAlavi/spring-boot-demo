# ci/tasks/package.sh

#!/bin/bash

echo $PWD
cd source_code
echo $PWD
ls -la
./mvnw package

