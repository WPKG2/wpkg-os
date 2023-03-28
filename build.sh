#!/bin/bash
echo "========================"
echo "WPKG OS build script"
echo "========================"

ARCHISO=archlive
TARGET=target
PACKAGES=packages

compile_packages=(
    "wpkgos-branding"
    "wpkgos-calamares-branding"
)

download_packages=(
    "https://wpkg.vercel.app/download/cli/wpkg2-cli.tar.zst"
    "https://built-aur.medzik.workers.dev/x86_64/calamares-3.2.49.1-1-x86_64.pkg.tar.xz"
)

WORKDIR=$TARGET/work
OUTDIR=$TARGET/out
REPODIR=$TARGET/repo

mkdir -p $WORKDIR $OUTDIR > /dev/null

if [ ! -e $REPODIR ]; then
    echo "Repo not exists... Creating..."
    mkdir $REPODIR

    #download packages
    echo "Downloading packages..."
    for i in "${download_packages[@]}"
    do
        wget -q --show-progress $i -P $REPODIR
    done

    #Building packages
    echo "Building packages..."
    for i in "${compile_packages[@]}"
    do
        if [ -e ./packages/$i ]; then
            ./packages/buildpkg.sh ./packages/$i ./$REPODIR
        else
            echo "Package $i not exists"
        fi
    done

    echo "Updating database..."
    repo-add $REPODIR/wpkgos.db.tar.gz $REPODIR/*
else
    echo "Repo exists continuing..."
fi

echo "Preparing archlive..."
TARGET_ARCHISO=$TARGET/archlive
sudo cp -r $ARCHISO $TARGET_ARCHISO
sudo chown -R $USER:$USER $TARGET_ARCHISO
sed -i "s#REPO_WILL_BE_PLACED_HERE#file://$(readlink -fn $REPODIR)#g" $TARGET_ARCHISO/pacman.conf

echo "Building ISO..."
sudo mkarchiso -w $WORKDIR -o $OUTDIR $TARGET_ARCHISO
