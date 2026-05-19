import React, { useEffect, useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import api from '../../services/api';
import './Dashboard.css';

function Dashboard() {
    const [user, setUser] = useState(null);
    const [stats, setStats] = useState({ disponiveis: 0, pendentes: 0, concluidas: 0 });
    const navigate = useNavigate();

    useEffect(() => {
        const loggedUser = JSON.parse(localStorage.getItem('user'));
        if (!loggedUser) {
            navigate('/login');
        } else {
            setUser(loggedUser);
            carregarEstatisticas(loggedUser);
        }
    }, [navigate]);

    const carregarEstatisticas = async (usuario) => {
        try {
            // Simulando busca de totais para os cards de resumo
            const resAlimentos = await api.get('/alimento/disponiveis');
            const resSolicitacoes = usuario.tipoUsuario === 'DOADOR' 
                ? await api.get(`/solicitacao/pendentes/${usuario.id}`)
                : await api.get(`/solicitacao/minhas-solicitacoes/${usuario.id}`);
            
            setStats({
                disponiveis: resAlimentos.data.length,
                pendentes: resSolicitacoes.data.filter(s => s.status === 'PENDENTE').length,
                concluidas: resSolicitacoes.data.filter(s => s.status !== 'PENDENTE').length
            });
        } catch (err) {
            console.error("Erro ao carregar estatísticas", err);
        }
    };

    if (!user) return null;

    return (
        <div className="dashboard-container">
            <header className="dashboard-header">
                <div>
                    <h1>Olá, {user.nome} 👋</h1>
                    <p>Bem-vindo ao painel do <strong>{user.tipoUsuario}</strong></p>
                </div>
                <div className="header-actions">
                    <span className="user-badge">{user.tipoUsuario}</span>
                </div>
            </header>

            <div className="stats-grid">
                <div className="stat-card">
                    <h3>Alimentos</h3>
                    <p className="stat-value">{stats.disponiveis}</p>
                    <p className="stat-label">Disponíveis na rede</p>
                </div>
                <div className="stat-card warning">
                    <h3>Pendentes</h3>
                    <p className="stat-value">{stats.pendentes}</p>
                    <p className="stat-label">Aguardando ação</p>
                </div>
                <div className="stat-card success">
                    <h3>Concluídas</h3>
                    <p className="stat-value">{stats.concluidas}</p>
                    <p className="stat-label">Total de doações</p>
                </div>
            </div>

            <section className="quick-actions">
                <h2>Ações Rápidas</h2>
                <div className="actions-grid">
                    {user.tipoUsuario === 'DOADOR' ? (
                        <>
                            <Link to="/gerenciamento-alimentos" className="action-btn main">
                                Gerenciar Meus Alimentos
                            </Link>
                            <Link to="/solicitacoes" className="action-btn">
                                Ver Solicitações Recebidas
                            </Link>
                        </>
                    ) : (
                        <>
                            <Link to="/solicitacoes" className="action-btn main">
                                Buscar Alimentos
                            </Link>
                            <Link to="/historico" className="action-btn">
                                Meu Histórico de Pedidos
                            </Link>
                        </>
                    )}
                    <Link to="/swagger" className="action-btn outline">Documentação API</Link>
                </div>
            </section>
        </div>
    );
}

export default Dashboard;