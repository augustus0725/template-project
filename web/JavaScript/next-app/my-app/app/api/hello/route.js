//  /api/hello
export async function GET(request) {
    console.log("It's fetching............")
    const users = [
        {id: 1, name: 'John'},
        {id: 2, name: 'Jane'},
        {id: 3, name: 'Bob'},
    ];
    return new Response(JSON.stringify(users));
}