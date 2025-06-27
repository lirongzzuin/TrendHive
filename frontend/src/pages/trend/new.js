

import { useState } from 'react';
import { useRouter } from 'next/router';
import axios from 'axios';

export default function NewTrendPage() {
  const router = useRouter();

  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [category, setCategory] = useState('');
  const [sourceUrl, setSourceUrl] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem('token');
      const response = await axios.post(
        'http://localhost:8080/api/trends',
        {
          title,
          description,
          category,
          sourceUrl,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      router.push(`/trend/${response.data.id}`);
    } catch (error) {
      console.error('트렌드 등록 실패:', error);
      alert('트렌드 등록에 실패했습니다.');
    }
  };

  return (
    <div style={{ maxWidth: '600px', margin: '0 auto', padding: '2rem' }}>
      <h1>트렌드 등록</h1>
      <form onSubmit={handleSubmit}>
        <label>
          제목:
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </label>
        <br /><br />
        <label>
          설명:
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
          />
        </label>
        <br /><br />
        <label>
          카테고리:
          <select
            value={category}
            onChange={(e) => setCategory(e.target.value)}
            required
          >
            <option value="">선택하세요</option>
            <option value="기술">기술</option>
            <option value="비즈니스">비즈니스</option>
            <option value="문화">문화</option>
            <option value="기타">기타</option>
          </select>
        </label>
        <br /><br />
        <label>
          출처 URL:
          <input
            type="url"
            value={sourceUrl}
            onChange={(e) => setSourceUrl(e.target.value)}
          />
        </label>
        <br /><br />
        <button type="submit">등록하기</button>
      </form>
    </div>
  );
}