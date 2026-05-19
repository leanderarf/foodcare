import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { login } from '../../services/authService';
import './Login.css';

function Login() {
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [loading, setLoading] = useState(false);

    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            const data = await login(email, senha);
            localStorage.setItem('user', JSON.stringify(data));
            
            // Redireciona para a dashboard que agora centraliza as novas funcionalidades
            navigate('/dashboard');
        } catch (err) {
            alert(err || "Falha na autenticação. Verifique suas credenciais.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="login-container">
            <div className="login-card">
                <div className="login-header">
                    <h2>Bem-vindo ao Food Care</h2>
                    <p>Entre com seus dados para gerenciar suas doações.</p>
                </div>

                <form onSubmit={handleLogin} className="login-form">
                    <div className="form-group">
                        <label>E-mail</label>
                        <input 
                            type="email" 
                            placeholder="exemplo@email.com" 
                            value={email} 
                            onChange={(e) => setEmail(e.target.value)} 
                            required 
                        />
                    </div>

                    <div className="form-group">
                        <label>Senha</label>
                        <input 
                            type="password" 
                            placeholder="********" 
                            value={senha} 
                            onChange={(e) => setSenha(e.target.value)} 
                            required 
                        />
                    </div>

                    <button type="submit" className="btn-login" disabled={loading}>
                        {loading ? 'Autenticando...' : 'Entrar'}
                    </button>
                </form>

                <div className="login-footer">
                    <p>Ainda não tem uma conta? <Link to="/cadastro">Cadastre-se aqui</Link></p>
                </div>
            </div>
        </div>
    );
}

export default Login;