# -*- coding: utf-8 -*-
import os
import shutil
import sys


def sed_min_project(p, base_package, app_name):
    base_package_path = base_package.replace(".", os.path.sep)
    old_path = "com.example.adam".replace(".", os.path.sep)
    for root, dirs, files in os.walk(p):
        for f in files:
            # 重新构建包路径
            target_root = root.replace(old_path, base_package_path).replace("adam", app_name)
            if f.endswith(".java"):
                # create target path
                fp = app_name
                for p in os.path.join(target_root, f).split(os.path.sep):
                    if p.endswith(".java"):
                        break
                    if not os.path.exists(os.path.join(fp, p)):
                        print("create dir {}".format(os.path.join(fp, p)))
                        os.mkdir(os.path.join(fp, p))
                    fp = os.path.join(fp, p)
                # copy java
                fp = os.path.join(app_name, target_root, f)
                print("copy java {} =============> {}".format(fp, os.path.join(root, f)))
                with open(fp, "w+", encoding="utf-8") as w:
                    with open(os.path.join(root, f), "r", encoding="utf-8") as r:
                        for v in r:
                            line = v.replace("com.example.adam", base_package)
                            w.write(line)
            else:
                # 路径不存在创建路径
                tp = os.path.join(target_root, f).split(os.path.sep)
                tp.pop()
                ap = app_name
                for p in tp:
                    ap = os.path.join(ap, p)
                    if not os.path.exists(ap):
                        print("create dir {}".format(ap))
                        os.mkdir(ap)
                if f.endswith("pom.xml") or f.endswith(".yaml") \
                        or f.endswith("Dockerfile") or f.endswith("docker-entrypoint.sh"):
                    sed_file(os.path.join(root, f), os.path.join(app_name, target_root, f), base_package, app_name)
                else:
                    sp = os.path.join(root, f)
                    dp = os.path.join(app_name, root.replace(old_path, base_package_path).replace("adam", app_name), f)
                    # 直接拷贝文件
                    print("copy {} =======================> {}".format(sp, dp))
                    shutil.copyfile(sp, dp)


def sed_file(entry, dest_file, base_package, app_name):
    if entry.endswith(".gitlab-ci.yml") or entry.endswith("eve.py"):
        return
    # make sure the dir exist.
    dest_file_parts = dest_file.split(os.path.sep)
    dest_file_parts.pop()
    dest_file_parts.pop(0)
    fp = app_name
    for p in dest_file_parts:
        fp = os.path.join(fp, p)
        if not os.path.exists(fp):
            os.mkdir(fp)

    # create dest_file
    print("copy {} =======================> {}".format(entry, dest_file))
    with open(dest_file, "w+", encoding="utf-8") as w:
        with open(entry, "r", encoding="utf-8") as r:
            for l in r:
                w.write(l
                        .replace("com.example.adam", base_package)
                        .replace("spring-adam-app", app_name)
                        .replace("adam", app_name)
                        )


def is_binary(entry):
    binary_types = [".png", ".jpg", ".exe"]
    for t in binary_types:
        if entry.endswith(t):
            return True
    return False


def sed_modules_project(jp, base_package, app_name):
    for p in os.listdir(jp):
        fp = os.path.join(jp, p)
        tp = fp.replace("adam", app_name)
        if os.path.isdir(fp):
            if os.path.exists(os.path.join(fp, "src")):
                sed_min_project(fp, base_package, app_name)
        else:
            sed_file(fp, os.path.join(app_name, fp.replace("adam", app_name)), base_package, app_name)


def copy_mvn_projects(root_entries, base_package, app_name):
    mvn_projects = []
    for entry in root_entries:
        if os.path.isdir(entry):
            if entry.startswith("adam") and os.path.exists(os.path.join(entry, "pom.xml")):
                mvn_projects.append(entry)
    # process java projects
    for jp in mvn_projects:
        if os.path.exists(os.path.join(jp, "src")):
            sed_min_project(jp, base_package, app_name)
        else:
            sed_modules_project(jp, base_package, app_name)


def main(app_name, base_package):
    root_entries = os.listdir(".")
    # mkdir new project dir
    os.mkdir(app_name)
    # begin to build project.
    for entry in root_entries:
        if os.path.isdir(entry):
            if entry.endswith(".git"):
                continue
            new_entry = entry.replace("adam", app_name, 1)
            os.mkdir(os.path.join(app_name, new_entry))
        else:
            if is_binary(entry):
                new_entry = os.path.join(app_name, entry)
                print("copy {} =======================> {}".format(entry, new_entry))
                shutil.copyfile(entry, new_entry)
            else:
                sed_file(entry, os.path.join(app_name, entry), base_package, app_name)
        pass
    # copy mvn projects
    copy_mvn_projects(root_entries, base_package, app_name)


if __name__ == '__main__':
    if len(sys.argv) < 3:
        print("参数错误, 正确的用法: python3 eve.py <app name>  <base package>")
        sys.exit(-1)
    main(sys.argv[1], sys.argv[2])
