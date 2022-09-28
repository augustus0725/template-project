import { Input, Tree } from 'antd';
import React, { useMemo, useState } from 'react';
const { Search } = Input;
const x = 3;
const y = 2;
const z = 1;
const defaultData = [];

const generateData = (_level, _preKey, _tns) => {
  const preKey = _preKey || '0';
  const tns = _tns || defaultData;
  const children = [];

  for (let i = 0; i < x; i++) { //  i 属于 [0, 2]
    const key = `${preKey}-${i}`;  // key 0-0 0-1 0-2
    tns.push({
      title: key,
      key,
      // children
    });                 // tns -> [0-0:0-0, ]

    if (i < y) { // y -> 2
      children.push(key);
    }
  }

  if (_level < 0) {
    return tns;
  }

  const level = _level - 1;
  children.forEach((key, index) => {
    tns[index].children = [];
    return generateData(level, key, tns[index].children);
  });
};

// 树结构的数据
generateData(z);
const dataList = [];

// 线性的数据结构
const generateList = (data) => {
  for (let i = 0; i < data.length; i++) {
    const node = data[i];
    const { key } = node;
    dataList.push({
      key,
      title: key,
    });

    if (node.children) {
      generateList(node.children);
    }
  }
};

generateList(defaultData);

const getParentKey = (key, tree) => {
  let parentKey;

  for (let i = 0; i < tree.length; i++) {
    const node = tree[i];

    if (node.children) {
      // node 的直接子 包含 key 
      if (node.children.some((item) => item.key === key)) {
        // 这个node就是parent
        parentKey = node.key;
      } else if (getParentKey(key, node.children)) {
        // 求间接子包不包含 这个key
        parentKey = getParentKey(key, node.children);
      }
    }
  }

  return parentKey;
};

const App = () => {
  const [expandedKeys, setExpandedKeys] = useState([]);
  const [searchValue, setSearchValue] = useState('');
  const [autoExpandParent, setAutoExpandParent] = useState(true);

  const onExpand = (newExpandedKeys) => {
    setExpandedKeys(newExpandedKeys);
    setAutoExpandParent(false);
  };

  const onChange = (e) => {
    // 检索
    const { value } = e.target;
    // 需要展开的父节点
    const newExpandedKeys = dataList
      .map((item) => {
        // 找包含value的node
        if (item.title.indexOf(value) > -1) {
          // 如果包含 应该展开 父节点
          return getParentKey(item.key, defaultData);
        }

        return null;
      })
      // 下面这段代码比较2, 主要是将 null 的过滤掉了, 后面一段代码没有用途
      .filter((item, i, self) => item && self.indexOf(item) === i);
    setExpandedKeys(newExpandedKeys); // 包含 search key的父节点展开
    setSearchValue(value); // -> 导致 treeData 变化, 重新画tree
    setAutoExpandParent(true);
  };

// 检索过的treeData, 包含处理过的title, children, 以及key( 这个没有直接用，不和title相等会怎样？ 不采用前缀的方式会怎样？)
  const treeData = useMemo(() => {
    const loop = (data) =>
      data.map((item) => {
        const strTitle = item.title;
        const index = strTitle.indexOf(searchValue);
        const beforeStr = strTitle.substring(0, index);
        const afterStr = strTitle.slice(index + searchValue.length);
        const title =
          index > -1 ? (
            <span>
              {beforeStr}
              <span className="site-tree-search-value">{searchValue}</span>
              {afterStr}
            </span>
          ) : (
            <span>{strTitle}</span>
          );

        if (item.children) {
          return {
            title,
            key: item.key,
            children: loop(item.children),
          };
        }

        return {
          title,
          key: item.key,
        };
      });

    return loop(defaultData);
  }, [searchValue]);
  return (
    <div>
      <Search
        style={{
          marginBottom: 8,
        }}
        placeholder="Search"
        onChange={onChange}
      />
      <Tree
        onExpand={onExpand}
        expandedKeys={expandedKeys}
        autoExpandParent={autoExpandParent}
        treeData={treeData}
      />
    </div>
  );
};

export default App;