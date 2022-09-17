import Nav from '@/components/nav';
import styles from './index.less';

const BaseLayouts = (props) => {
  return (
    <>
      <div className={styles.nav}><Nav></Nav></div>
      { props.children }
    </>
  );
};

export default BaseLayouts;
