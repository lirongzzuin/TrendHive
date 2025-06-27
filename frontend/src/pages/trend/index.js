

import { useEffect, useState } from 'react';
import Link from 'next/link';

export default function TrendListPage() {
  const [trends, setTrends] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchTrends() {
      try {
        const response = await fetch('/api/trends');
        const data = await response.json();
        setTrends(data);
      } catch (error) {
        console.error('트렌드를 불러오는데 실패했습니다:', error);
      } finally {
        setLoading(false);
      }
    }

    fetchTrends();
  }, []);

  if (loading) return <p>불러오는 중...</p>;

  return (
    <div style={{ padding: '2rem' }}>
      <h1>트렌드 목록</h1>
      <ul style={{ listStyle: 'none', padding: 0 }}>
        {trends.length > 0 ? (
          trends.map((trend) => (
            <li key={trend.id} style={{ marginBottom: '1rem' }}>
              <Link href={`/trend/${trend.id}`}>
                <a>
                  <h3>{trend.title}</h3>
                  <p>{trend.description}</p>
                </a>
              </Link>
            </li>
          ))
        ) : (
          <p>등록된 트렌드가 없습니다.</p>
        )}
      </ul>
    </div>
  );
}