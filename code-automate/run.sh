#!/bin/bash

GIT_SOURCE_PATH=/home/darwin/workspace/opensource/elasticsearch_loader-001
DEST_SOURCE_PATH=/home/darwin/workspace/opensource/dest
# jira-id author
MEMBERS=member.list
TARGET_LINES=150

base=$(dirname $0)
cd $base

# import some functions
. ./common.sh

set -x
set -e

# do check
[ ! -e $GIT_SOURCE_PATH ] && echo "Git source path : $GIT_SOURCE_PATH not exist" && exit -1
[ ! -e $DEST_SOURCE_PATH ] && echo "Dest path : $DEST_SOURCE_PATH not exist" && exit -1
if [ ! -e $DEST_SOURCE_PATH/.git ]; then
  echo "Dest path: $DEST_SOURCE_PATH should contain .git"
  exit -1
fi

# prepare log
[ ! -e logs ] && mkdir -p logs
log_file=`date "+%Y%m%d%H%M%S"`
exec &> >(tee -a "logs/$log_file") 2>&1

# config git
echo "Config git."
if [ -e ssh-auth ]; then
  # backup old
  [ -e ~/.ssh ] && mv ~/.ssh ~/.ssh-orig
  [ ! -e ~/.ssh ] && mkdir -p ~/.ssh
  cp ssh-auth/* ~/.ssh/
  chmod a+r ~/.ssh/*
fi

# check history info, read more info then we know where to continue
if [ -e history-dir ]; then
  echo "History info exist."
else
  generate_history "$GIT_SOURCE_PATH"
  move_modified_files "$GIT_SOURCE_PATH" "1"
fi

#
# 读这个文件给每个用户提交代码,出错的话也就是代码多提交了一些
# history-dir/current
#
while read line;
do
  # 第一层循环,遍历每个用户
  total=0
  #
  jiraid=`echo $line | awk '{ print $1 }'`
  author=`echo $line | awk '{ print $2 }'`
  author_email=`echo $line | awk '{ print $3 }'`

  # create new branch if branch not exist
  cd $DEST_SOURCE_PATH
  # maybe errors occur last time not to push success.
  #TODO git push
  git checkout -b "$jiraid"
  cd -

  while true;
  do
    # 死循环, 读取足够多的code
    if [ $total -gt $TARGET_LINES ]; then
      # 做好提交工作
      echo "Ready to commit code."
      cd $DEST_SOURCE_PATH
      git add *
      git commit -a -m "#$jiraid#" --author="$author $author_email"
      #TODO git push
      cd -
      break
    fi

    # 处理已经存在的新代码
    while read filename_lines;
    do
      filename=`echo $filename_lines | awk '{ print $1 }'`
      lines=`echo $filename_lines | awk '{ print $2 }'`

      # 文件可能已经不存在,需要判断一下
      if [ ! -e history-dir/files-modified/$filename ]; then
        continue
      fi
      ((total=$total+$lines))
      #移动文件到目标位置
      dest_dir=`dirname $filename`
      [ ! -e $DEST_SOURCE_PATH/$dest_dir ] && mkdir -p $DEST_SOURCE_PATH/$dest_dir
      cp -f history-dir/files-modified/$filename $DEST_SOURCE_PATH/$dest_dir
      rm -rf history-dir/files-modified/$filename
    done < history-dir/current

    # 查看提交的代码数是不是已经满足需求, 不然需要处理下一个commit
    if [ $total -lt $TARGET_LINES ]; then
      # 先做一些清理
      truncate -s 0 history-dir/current     
      # 读取下一个commit hash
      next_seq=`cat history-dir/seq`
      ((next_seq=$next_seq+1))
      # 检查是不是所有的commit已经用完了
      max_seq=`wc -l history-dir/commits_list | awk '{ print $1 }'`
      if [ $next_seq -gt $max_seq ]; then
        echo "!!!Warnning! All commits run out."
        exit 0
      fi
      move_modified_files "$GIT_SOURCE_PATH" "$next_seq"
    fi
  done
done < member.list
