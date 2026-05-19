import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Header.css';

function Header() {
    const [menuOpen, setMenuOpen] = useState(false);
    const navigate = useNavigate();
    const user = JSON.parse(localStorage.getItem('user'));

    const handleLogout = () => {
        localStorage.removeItem('user');
        navigate('/login');
    };

    return (
        <header className="header-container">
            <div className="header-content">
                <div className="logo-wrapper">
                    <img src="/images/logo.png" alt="FoodCare Logo" className="logo-image" />
                    <Link to="/" className="logo">Food<span>Care</span></Link>
                </div>

                <div className={`menu-section ${menuOpen ? 'on' : ''}`} onClick={() => setMenuOpen(!menuOpen)}>
                    <div className="menu-toggle">
                        <div className="one"></div>
                        <div className="two"></div>
                        <div className="three"></div>
                    </div>

                    <nav>
                        <ul>
                            <li><Link to="/">Home</Link></li>
                            {user ? (
                                <>
                                    <li><Link to="/dashboard">Dashboard</Link></li>
                                    {user.tipoUsuario === 'DOADOR' && (
                                        <li><Link to="/gerenciamento-alimentos">Meus Alimentos</Link></li>
                                    )}
                                    <li><Link to="/solicitacoes">Solicitações</Link></li>
                                    <li><Link to="/historico">Histórico</Link></li>
                                    <li><button onClick={handleLogout} className="btn-logout">Sair</button></li>
                                </>
                            ) : (
                                <>
                                    <li><Link to="/login">Login</Link></li>
                                    <li><Link to="/cadastro" className="btn-cadastro">Cadastrar-se</Link></li>
                                </>
                            )}
                        </ul>
                    </nav>
                </div>
            </div>
        </header>
    );
}

export default Header;