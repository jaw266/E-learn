import { FormEvent, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Logo } from '../components/Logo';
import { useAuth } from '../state/AuthContext';

const domaines = ['Data & IA', 'Developpement Web', 'IoT', 'Cybersecurity', 'Business', 'Design'];
const niveaux = ['Debutant', 'Intermediaire', 'Avance'];

function getPasswordRules(password: string, name: string, email: string) {
  const emailPrefix = email.includes('@') ? email.split('@')[0].toLowerCase() : email.toLowerCase();
  return [
    { label: '8 caracteres minimum', ok: password.length >= 8 },
    { label: 'Une lettre majuscule', ok: /[A-Z]/.test(password) },
    { label: 'Une lettre minuscule', ok: /[a-z]/.test(password) },
    { label: 'Un chiffre', ok: /[0-9]/.test(password) },
    { label: 'Un caractere special (!@#$%...)', ok: /[!@#$%^&*()_+=\[\]{};:"\\|,.<>/?-]/.test(password) },
    { label: 'Ne contient pas votre nom', ok: name.length === 0 || !password.toLowerCase().includes(name.toLowerCase()) },
    { label: 'Ne contient pas votre email', ok: emailPrefix.length === 0 || !password.toLowerCase().includes(emailPrefix) },
  ];
}

export default function RegisterPage() {
  const { register } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({ name: '', email: '', password: '', niveau: 'Debutant', domaineInteret: 'Data & IA' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [pwdFocused, setPwdFocused] = useState(false);

  const rules = getPasswordRules(form.password, form.name, form.email);
  const passwordValid = rules.every((r) => r.ok);

  const onSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setError('');
    if (!passwordValid) {
      setError('Le mot de passe ne respecte pas toutes les regles.');
      return;
    }
    setLoading(true);
    try {
      await register(form);
      navigate('/');
    } catch (err: any) {
      const msg = err?.response?.data?.message;
      setError(msg || 'Impossible de creer le compte. Verifiez les informations.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className="auth-page">
      <div className="auth-card glass-card wide">
        <Logo />
        <h1>Creer un compte</h1>
        <p>Choisissez votre domaine pour recevoir des cours et livres adaptes.</p>
        <form onSubmit={onSubmit} className="form-grid two-cols">
          <label>Nom complet
            <input value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} required placeholder="Votre nom" />
          </label>
          <label>Email
            <input type="email" value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} required placeholder="votre@email.com" />
          </label>
          <label>Mot de passe
            <input
              type="password"
              value={form.password}
              onChange={(e) => setForm({ ...form, password: e.target.value })}
              onFocus={() => setPwdFocused(true)}
              required
              placeholder="Mot de passe securise"
            />
          </label>
          <label>Niveau
            <select value={form.niveau} onChange={(e) => setForm({ ...form, niveau: e.target.value })}>
              {niveaux.map((n) => <option key={n}>{n}</option>)}
            </select>
          </label>
          {(pwdFocused || form.password.length > 0) && (
            <ul className="pwd-rules full-field">
              {rules.map((r) => (
                <li key={r.label} style={{ color: r.ok ? '#22c55e' : '#ef4444' }}>
                  {r.ok ? '+' : 'x'} {r.label}
                </li>
              ))}
            </ul>
          )}
          <label className="full-field">Domaine d&apos;interet
            <select value={form.domaineInteret} onChange={(e) => setForm({ ...form, domaineInteret: e.target.value })}>
              {domaines.map((d) => <option key={d}>{d}</option>)}
            </select>
          </label>
          {error && <div className="alert error full-field">{error}</div>}
          <button className="btn btn-primary full-field" disabled={loading || !passwordValid}>
            {loading ? 'Creation...' : 'Creer mon compte'}
          </button>
        </form>
        <p className="auth-switch">Deja inscrit ? <Link to="/login">Se connecter</Link></p>
      </div>
    </section>
  );
}