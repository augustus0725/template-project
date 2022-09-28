import { Input, Tree } from 'antd';
import React, { useState, memo, useMemo } from 'react';
import styles from './index.less';
import CollapseIcon from '@/assets/common/open.svg';
import CollapsedIcon from '@/assets/common/close.svg';

const QueriableTree = ({ treeData }) => {
  const [hidden, setHidden] = useState(false);
  // search tree
  const QueriableTreeComponent = () => {
    const [expandedKeys, setExpandedKeys] = useState([]);
    const [searchValue, setSearchValue] = useState('');
    const [autoExpandParent, setAutoExpandParent] = useState(true);
    const { Search } = Input;

    const onExpand = (newExpandedKeys) => {
      setExpandedKeys(newExpandedKeys);
      setAutoExpandParent(false);
    };

    const traverseTree = (tree, f) => {
      if (tree.children) {
        f(tree);
        Array.from(tree.children).forEach((child) => {
          traverseTree(child, f);
        });
      } else {
        f(tree);
      }
    }

    const traverse = (forest, f) => {
      Array.from(forest).forEach((tree) => {
        traverseTree(tree, f);
      });
    };

    const getParent = (key, forest) => {
      let parentKey = null;

      traverse(forest, (node) => {
        if (node.children && node.children.some((item) => item.key === key)) {
          parentKey = node.key;
        }
      });

      return parentKey;
    };

    const onChange = (e) => {
      const { value } = e.target;
      const newExpandedKeys = [];

      traverse(treeData, (node) => {
        if (node.title.indexOf(value) > -1) {
          let parentKey = getParent(node.key, treeData);
          if (parentKey) {
            newExpandedKeys.push(parentKey);
          }
        }
      });
      setExpandedKeys(newExpandedKeys);
      setSearchValue(value);
      setAutoExpandParent(true);
    };

    const treeRenderData = useMemo(() => {
      const render = (data) =>
        data.map((item) => {
          const strTitle = item.title;
          const index = strTitle.indexOf(searchValue);
          const beforeStr = strTitle.substring(0, index);
          const afterStr = strTitle.slice(index + searchValue.length);
          const title =
            index > -1 ? (
              <span>
                {beforeStr}
                <span className={styles.highlight}>{searchValue}</span>
                {afterStr}
              </span>
            ) : (
              <span>{strTitle}</span>
            );

          if (item.children) {
            return {
              title,
              key: item.key,
              children: render(item.children),
            };
          }

          return {
            title,
            key: item.key,
          };
        });

      return render(treeData);
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
          treeData={treeRenderData}
        />
      </div>
    );
  };

  const CollapseDom = () => {
    return (
      <>
        {hidden && (
          <img
            alt=""
            src={CollapseIcon}
            className={styles.openicon}
            onClick={() => setHidden(!hidden)}
          />
        )}
        {!hidden && (
          <img
            alt=""
            src={CollapsedIcon}
            className={styles.openicon}
            onClick={() => setHidden(!hidden)}
          />
        )}
      </>
    );
  };

  return (
    <div className="flex flex-row">
      <div>{!hidden && <QueriableTreeComponent />}</div>
      <div>
        <CollapseDom />
      </div>
    </div>
  );
};

export default memo(QueriableTree);
