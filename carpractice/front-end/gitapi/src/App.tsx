import './App.css'
import {QueryClient, QueryClientProvider} from '@tanstack/react-query'; // 데이터 캐싱을 처리하는 라이브러리
import Repositories from './Repositories.tsx';

const queryClient = new QueryClient();

function App() {
  return (
    <>
    <QueryClientProvider client={queryClient}>
      <Repositories />
    </QueryClientProvider>
    </>
  )
}

export default App
