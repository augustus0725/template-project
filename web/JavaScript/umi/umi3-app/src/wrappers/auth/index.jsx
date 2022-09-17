import {Redirect} from 'umi';

export default (props) => {
  if (false) {
    return (
      <div>{props.children}</div>
    );
  } else {
    return <Redirect to='/login'></Redirect>
  }
}
