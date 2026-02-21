import { useQuery } from "@tanstack/react-query";
import axios from "axios";

interface Repository {
  id: number;
  full_name: string;
  html_url: string;
}

type GithubSearchResponse = {
  items: Repository[];
};

async function getRepositories(): Promise<Repository[]> {
  const response = await axios.get<GithubSearchResponse>(
    "https://api.github.com/search/repositories?q=react"
  );
  return response.data.items;
}

function Repositories() {
  const { isLoading, isError, data } = useQuery<Repository[]>({
    queryKey: ["repositories"], // 데이터를 구분하는 이름표(식별자)
    queryFn: getRepositories,   // 실제 데이터를 가져오는 함수
  });

  if (isLoading) return <p>Loading...</p>;
  if (isError) return <p>Error...</p>;

  // data는 undefined일 수 있으니 안전하게 처리
  const repos = data ?? [];

  return (
    <table>
      <tbody>
        {repos.map((repo) => (
          <tr key={repo.id}>
            <td>{repo.full_name}</td>
            <td>
              <a href={repo.html_url} target="_blank" rel="noreferrer">
                {repo.html_url}
              </a>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default Repositories;