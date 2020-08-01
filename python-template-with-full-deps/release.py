# -*- coding: utf-8 -*-

# cp bin build/
# cp .tox/release/Lib/* build/lib/
import codecs
import shutil
import stat
import sys
import os
import tarfile


def _find_version():
    current_version = "1.0.0"
    if os.path.exists("VERSION"):
        current_version = codecs.open("VERSION", "r", "utf-8").read()
        if not current_version or current_version == "":
            print("VERSION should not be empty!!!")
            sys.exit(-1)
    return current_version


def _make_bash_runnable(dir):
    for entry in os.listdir(dir):
        full = os.path.join(dir, entry)
        if os.path.isfile(full) and full.endswith('.sh'):
            os.chmod(full, stat.S_IXUSR | stat.S_IRUSR)


def _copy_tree(src, dest):
    """
    cp src/* dest
    """
    print("-----------------------")
    print(src)
    print('-----------------------')
    for entry in os.listdir(src):
        file_full_path = os.path.join(src, entry)
        if os.path.isfile(file_full_path):
            print("Copying {0}  -->  {1}".format(file_full_path, dest))
            shutil.copy2(file_full_path, dest)
        else:
            print("Copying {0}  -->  {1}".format(entry, dest))
            file_dest_path = os.path.join(dest, entry)
            if os.path.exists(file_dest_path):
                shutil.rmtree(file_dest_path)
            shutil.copytree(file_full_path, os.path.join(dest, entry))


def _copy_depends(root, site_package_dir):
    to_clean = ["dist"]
    for v in to_clean:
        if os.path.exists(v):
            print("removing directory : {}".format(v))
            shutil.rmtree(v)
    os.mkdir("dist")
    # mkdir project name dir
    project_name = os.path.realpath(__file__).split(os.path.sep)[-2] + "-" + _find_version()
    os.mkdir(os.path.join("dist", project_name))
    os.mkdir(os.path.join("dist", project_name, "lib"))
    # 将依赖的python库都保存到lib
    _copy_tree(site_package_dir, os.path.join(root, 'dist', project_name, 'lib'))
    # 如果存在其他需要拷贝的目录，写在package.list
    if os.path.exists('package.list'):
        print('-----------------------')
        print('Add other data needed in this project.')
        print('-----------------------')
        with open(os.path.join(root, 'package.list'), 'r') as ar:            
            for line in ar.readlines():
                if not line or "" == line:
                    continue
                entry = line.strip("\r\n ")
                print("Copying {0}  -->  {1}".format(os.path.join(root, entry), os.path.join(root, 'dist', project_name)))
                if os.path.isdir(entry):
                    if not os.path.exists(os.path.join(root, 'dist', project_name, entry)):
                        os.mkdir(os.path.join(root, 'dist', project_name, entry))
                    _copy_tree(os.path.join(root, entry), os.path.join(root, 'dist', project_name, entry))
                if os.path.isfile(entry):
                    shutil.copy2(os.path.join(root, entry), os.path.join(root, 'dist', project_name))
    # 最后打包
    os.chdir('dist')
    tar = tarfile.open(project_name + '.tar.gz', "w:gz")
    tar.add(project_name)
    tar.close()


def main():
    # permissions
    bin_dir = os.path.join(sys.argv[1], 'bin')
    if os.path.exists(bin_dir):
        _make_bash_runnable(bin_dir)
    _copy_depends(sys.argv[1], sys.argv[2])


if __name__ == '__main__':
    main()
