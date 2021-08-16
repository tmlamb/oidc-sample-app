import { useQuery, gql } from '@apollo/client';

interface Author {
  id: string;
  firstName: string;
  lastName: string;
}

interface BookByIdData {
  bookById: Book
}

interface Book {
  id: string;
  name: string;
  pageCount: number;
  author: Author;
}

interface BookByIdVars {
  id: string;
}

const GET_BOOK_INVENTORY = gql`
query ($id: ID!) {  
  bookById(id: $id) {    
    id
    name
    pageCount
    author {
      id
      firstName
      lastName
    }
  }
}
`;

const Books = () => {

  const { loading, data } = useQuery<BookByIdData, BookByIdVars>(
    GET_BOOK_INVENTORY,
    { variables: { id: "book-1" } }
  );
  console.log(data);
  return (
    <div>
      <div>
        <h3>Available Inventory</h3>
        {loading ? (
          <p>Loading ...</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Id</th>
                <th>Name</th>
                <th>PageCount</th>
                <th>Author</th>
              </tr>
            </thead>
            <tbody>
                <tr>
                  <td>{data && data.bookById && data.bookById.id}</td>
                  <td>{data && data.bookById && data.bookById.name}</td>
                  <td>{data && data.bookById && data.bookById.pageCount}</td>
                </tr>
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
};

export default Books;
