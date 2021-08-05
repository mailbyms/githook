#!/bin/bash
echo "begin to clone $2"
project_dir="/tmp/$1"
rm -fr "$project_dir"
mkdir -p "$project_dir"

git clone --mirror "$2" "$project_dir"
cd "$project_dir"
echo "complete ."

echo "push to gitlab"
git remote add --mirror=fetch secondary "git@192.168.0.101:Deyang-ZXJY/$1.git"
git push secondary --all

echo "done"

rm -fr "$project_dir"

