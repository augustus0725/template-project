export default function page({params}) {
    return (
        <>
            <p>...slug: {params.slug[0]}</p>
            <p>...slug: {params.slug[1]}</p>
            <p>...slug: {params.slug[100]}</p>
        </>
    )
}