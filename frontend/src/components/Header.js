

import React from 'react';
import Link from 'next/link';
import { useRouter } from 'next/router';

const Header = () => {
  const router = useRouter();
  const token = typeof window !== 'undefined' ? localStorage.getItem('token') : null;

  const handleLogout = () => {
    localStorage.removeItem('token');
    router.push('/login');
  };

  return (
    <header style={styles.header}>
      <nav style={styles.nav}>
        <Link href="/" style={styles.link}>Home</Link>
        <Link href="/dashboard" style={styles.link}>Dashboard</Link>
        {token ? (
          <>
            <Link href="/trend/create" style={styles.link}>New Trend</Link>
            <button onClick={handleLogout} style={styles.button}>Logout</button>
          </>
        ) : (
          <>
            <Link href="/login" style={styles.link}>Login</Link>
            <Link href="/signup" style={styles.link}>Signup</Link>
          </>
        )}
      </nav>
    </header>
  );
};

const styles = {
  header: {
    padding: '1rem',
    borderBottom: '1px solid #ccc',
    marginBottom: '2rem'
  },
  nav: {
    display: 'flex',
    gap: '1rem',
    alignItems: 'center'
  },
  link: {
    textDecoration: 'none',
    color: '#0070f3'
  },
  button: {
    backgroundColor: 'transparent',
    border: 'none',
    color: '#0070f3',
    cursor: 'pointer'
  }
};

export default Header;