#!bin/bash
git clone $1
helm install $2
helm test $3
helm uninstall