#!/bin/sh

# makepkg helper script for building packages for Arch Linux

TARGET=$PWD/target/tmp
PACKAGE=$1
DIST=$2

mkdir -p $TARGET
cp -r $PACKAGE/* $TARGET/

OPTIONS=""
if [ -e $PACKAGE/options ]; then
    OPTIONS=$(<$PACKAGE/options tr -d '\n')
    echo "Options was: $OPTIONS"
fi

cd $TARGET && makepkg -s $OPTIONS && cd ../..

cp $TARGET/*tar* $DIST

rm -r $PWD/target/tmp
