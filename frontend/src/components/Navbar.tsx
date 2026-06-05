import { NavLink, useNavigate } from 'react-router-dom';
import { useAuth } from '../state/AuthContext';
import { Logo } from './Logo';

export function Navbar() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  return (
    <header className="navbar">
      <Logo />
      <nav className="nav-links">
        <NavLink to="/">Accueil</NavLink>
        <NavLink to="/catalogue">Catalogue</NavLink>
        <NavLink to="/livres">Livres gratuits</NavLink>
        <NavLink to="/dashboard">Dashboard</NavLink>
      </nav>
      <div className="nav-user">
        {user ? (
          <>
            <div className="avatar">{user.name.charAt(0).toUpperCase()}</div>
            <div className="user-meta">
              <strong>{user.name}</strong>
              <span>{user.domaineInteret}</span>
            </div>
            <button className="btn btn-soft" onClick={() => { logout(); navigate('/login'); }}>Déconnexion</button>
          </>
        ) : (
          <button className="btn btn-primary" onClick={() => navigate('/login')}>Connexion</button>
        )}
      </div>
    </header>
  );
}
