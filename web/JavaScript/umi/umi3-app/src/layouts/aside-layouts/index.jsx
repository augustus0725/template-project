import Menu from '@/components/menu';
import styles from './index.less'

const AsideLayout = (props) => {
  return (
    <div>
      <div className={styles.left}>
        <Menu></Menu>
        </div>
      <div>{ props.children }</div>
    </div>
  );
}

export default AsideLayout;
