import { useParams, useLocation, useRouteMatch } from "umi";

export default function ({ location: {search}, match: { params: {id} } }) {
  const params = useParams();
  // const {id} = useParams();
  const location = useLocation();
  // const {search} = useLocation();

  return (
    <>
      <div> It's detail for the goods. </div>
      <div> 用路由上下文获取path的参数: {id} </div>
      <div> 用路由上下文获取query的参数: {search} </div>
      <br/>
      <br/>
      <div> 用hook获取path的参数: {params.id} </div>
      <div> 用hook获取query的参数: {location.search} </div>
    </>
  );
}
