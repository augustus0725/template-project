import {Redirect} from 'umi';

export default (props) => {
  if (true) {
    return (
      <div>{props.children}</div>
    );
  } else {
    return <Redirect to='/login'></Redirect>
  }
}
