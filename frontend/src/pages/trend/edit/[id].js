

import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import axios from "axios";

export default function EditTrendPage() {
  const router = useRouter();
  const { id } = router.query;
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [category, setCategory] = useState("");
  const [sourceUrl, setSourceUrl] = useState("");

  useEffect(() => {
    if (id) {
      axios
        .get(`/api/trends/${id}`)
        .then((res) => {
          const { title, description, category, sourceUrl } = res.data;
          setTitle(title);
          setDescription(description);
          setCategory(category);
          setSourceUrl(sourceUrl);
        })
        .catch((err) => {
          console.error("Failed to fetch trend:", err);
        });
    }
  }, [id]);

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await axios.patch(`/api/trends/${id}`, {
        title,
        description,
        category,
        sourceUrl,
      });
      router.push(`/trend/${id}`);
    } catch (err) {
      console.error("Failed to update trend:", err);
    }
  };

  return (
    <div className="container">
      <h1>트렌드 수정</h1>
      <form onSubmit={handleUpdate}>
        <input
          type="text"
          placeholder="제목"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />
        <textarea
          placeholder="설명"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          required
        />
        <input
          type="text"
          placeholder="카테고리"
          value={category}
          onChange={(e) => setCategory(e.target.value)}
        />
        <input
          type="url"
          placeholder="출처 링크"
          value={sourceUrl}
          onChange={(e) => setSourceUrl(e.target.value)}
        />
        <button type="submit">수정 완료</button>
      </form>
    </div>
  );
}