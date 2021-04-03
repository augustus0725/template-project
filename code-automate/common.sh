#!/bin/bash

function generate_history {
  git_source="$1"
  # generate raw commit list
  cd "$git_source"
  git checkout master
  git log --pretty=oneline > raw_log.out
  cd -
  # move raw data to workdir
  [ ! -e history-dir ] && mkdir -p history-dir
  mv "$git_source/raw_log.out" history-dir
  cd history-dir
  # get commit list
  awk '{ print $1 }' raw_log.out > commits_list
  cd ..
  # no info left if we first build history 
  rm -rf history-dir/raw_log.out
}


#
# 输入目录有哪些文件, 每个文件多少行
# 结果输出到 history-dir/current
#
function lines_added_every_file {
  target_dir=$1
  cd $target_dir
  for entry in `find .`;
  do
    if [ -d $entry ]; then
      continue
    else
      lines=`wc -l $entry | awk '{ print $1 }'`
      echo "$entry $lines" >> ../current
    fi
  done
  cd -
}

#
# 输出 history-dir/current, 告诉你当前有哪些修改的文件以及文件的行数
# 输出 history-dir/seq, 告诉你用到哪个commit hash
#
function move_modified_files {
  git_source="$1"
  nth="$2"

  total_lines=`wc -l history-dir/commits_list | awk '{ print $1 }'`
  ((nth=$total_lines-$nth+1))
  # get nth line
  nth_line=`sed "${nth}q;d" history-dir/commits_list`

  cd $git_source
  git checkout $nth_line

  cd -
  if [ $2 -eq 1 ]; then
    # 第一次进来说明, 第一个commit里内容都是新加的
    cp -a $git_source history-dir/files-modified
    rm -rf history-dir/files-modified/.git
    # 统计一下每个文件多少行
    lines_added_every_file "history-dir/files-modified"
    # 标记一下使用到第几个commit hash了
    echo 1 > history-dir/seq
    return
  fi
  
  # 不是第一次进来获取前一个版本的commit hash, 使用命令来获取修改的文件, 以及修改的行数
  ((nth=$nth+1))
  last_commit=`sed "${nth}q;d" history-dir/commits_list`

  cd $git_source
  # 获取两个commit hash的差异
  git diff --compact-summary "$last_commit" "$nth_line" > tmp.diff
  
  cd -
  rm -rf history-dir/files-modified
  # 分析tmp.diff, 获取每行
  while read line;
  do
    if echo "$line" | grep '|'; then
      filename=`echo $line | cut -d "|" -f 1 | awk '{ print $1 }'`
      num=`echo $line | cut -d "|" -f 2 | awk '{ print $1 }'`
      # output to a file
      echo "$filename $num" >> history-dir/current
      path=`dirname $filename`
      [ ! -e  history-dir/files-modified/$path ] && mkdir -p history-dir/files-modified/$path
      cp $git_source/$filename history-dir/files-modified/$filename      
    fi
  done < $git_source/tmp.diff
  rm -rf $git_source/tmp.diff

  echo "$2" > history-dir/seq
}
