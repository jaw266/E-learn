import { createContext, useContext, useMemo, useState } from 'react';
import { api } from '../api/client';
import type { User } from '../types';

type RegisterPayload = {
  name: string;
  email: string;
  password: string;
  niveau: string;
  domaineInteret: string;
};

type AuthContextValue = {
  user: User | null;
  token: string | null;
  login: (email: string, password: string) => Promise<void>;
  register: (payload: RegisterPayload) => Promise<void>;
  logout: () => void;
};

const AuthContext = createContext<AuthContextValue | undefined>(undefined);

const readUser = () => {
  const raw = localStorage.getItem('elearn_user');
  return raw ? (JSON.parse(raw) as User) : null;
};

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [token, setToken] = useState<string | null>(() => localStorage.getItem('elearn_token'));
  const [user, setUser] = useState<User | null>(() => readUser());

  const saveSession = (data: { token: string; name: string; email: string; niveau: string; domaineInteret: string; role: string }) => {
    localStorage.setItem('elearn_token', data.token);
    const loggedUser: User = {
      name: data.name,
      email: data.email,
      niveau: data.niveau,
      domaineInteret: data.domaineInteret,
      role: data.role,
    };
    localStorage.setItem('elearn_user', JSON.stringify(loggedUser));
    setToken(data.token);
    setUser(loggedUser);
  };

  const login = async (email: string, password: string) => {
    const { data } = await api.post('/auth/login', { email, password });
    saveSession(data);
  };

  const register = async (payload: RegisterPayload) => {
    const { data } = await api.post('/auth/register', payload);
    saveSession(data);
  };

  const logout = () => {
    localStorage.removeItem('elearn_token');
    localStorage.removeItem('elearn_user');
    setToken(null);
    setUser(null);
  };

  const value = useMemo(() => ({ user, token, login, register, logout }), [user, token]);

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth must be used inside AuthProvider');
  return ctx;
}
