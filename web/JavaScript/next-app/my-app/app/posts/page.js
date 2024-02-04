// 静态的metadata
// export const metadata = {
//     title: 'post'
// }

// 动态的metadata
export async function generateMetadata({params, searchParams}) {
    const res = await fetch('http://localhost:3000/api/hello',
        {next: {revalidate: 10}})
    const data = await res.json();

    return { title: data[0].name}
}

export default async function page() {
    const res = await fetch('http://localhost:3000/api/hello',
        {next: {revalidate: 10}})
    const data = await res.json();

    // ssr- server side rendering 这个是服务端组件, 数据会下载下来变成静态网页传到前端
    // ssg- static site generation
    // isr- incremental static generation  ==>  带 next: {revalidate: 10} 会过一段时间生成一次

    return (
        <>
            <p>This is post page!</p>
            <ul>
                {data.map((v, idx) => {
                    return (<li key={idx}>{v.id} : {v.name}</li>)
                })}
            </ul>
        </>
    );
}