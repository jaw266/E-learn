import { FormEvent, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Logo } from '../components/Logo';
import { useAuth } from '../state/AuthContext';

export default function LoginPage() {
  const { login } = useAuth();
  const navigate = useNavigate();
  const [email, setEmail] = useState('demo@elearnpro.com');
  const [password, setPassword] = useState('password');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const onSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      await login(email, password);
      navigate('/');
    } catch {
      setError('Email ou mot de passe incorrect.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className="auth-page">
      <div className="auth-card glass-card">
        <Logo />
        <h1>Bienvenue</h1>
        <p>Connectez-vous pour accéder à votre bibliothèque e-learning intelligente.</p>
        <form onSubmit={onSubmit} className="form-grid">
          <label>Email
            <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required placeholder="votre@email.com" />
          </label>
          <label>Mot de passe
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required placeholder="••••••••" />
          </label>
          {error && <div className="alert error">{error}</div>}
          <button className="btn btn-primary full" disabled={loading}>{loading ? 'Connexion...' : 'Se connecter'}</button>
        </form>
        <p className="auth-switch">Pas encore de compte ? <Link to="/register">Créer un compte</Link></p>
      </div>
      <div className="auth-hero">
        <span className="badge">Design pro</span>
        <h2>Apprendre plus vite avec des recommandations par domaine.</h2>
        <p>Catalogue, livres gratuits, progression, dashboard et gestion de bibliothèque en une seule plateforme.</p>
      </div>
    </section>
  );
}
