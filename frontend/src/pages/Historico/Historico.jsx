import React, { useState, useEffect } from 'react';
import api from '../../services/api';
import './Historico.css';

function Historico() {
    const [historico, setHistorico] = useState([]);
    const [loading, setLoading] = useState(true);
    const user = JSON.parse(localStorage.getItem('user'));

    useEffect(() => {
        carregarHistorico();
    }, []);

    const carregarHistorico = async () => {
        try {
            // Buscamos todas as solicitações (finalizadas ou não) para compor o histórico
            const rota = user.tipoUsuario === 'DOADOR' 
                ? `/solicitacao/doador/${user.id}` // Rota para ver tudo que enviaram para ele
                : `/solicitacao/minhas-solicitacoes/${user.id}`; // Rota para ver o que ele pediu
            
            const response = await api.get(rota);
            setHistorico(response.data || []);
        } catch (err) {
            console.error("Erro ao carregar histórico", err);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="historico-container">
            <header className="historico-header">
                <h2>Histórico de Atividades</h2>
                <p>Consulte aqui o registro completo de suas movimentações no FoodCare.</p>
            </header>

            {loading ? <p>Carregando registros...</p> : (
                <div className="historico-list">
                    {historico.length === 0 ? (
                        <div className="empty-state">
                            <p>Nenhum registro encontrado.</p>
                        </div>
                    ) : (
                        <table className="historico-table">
                            <thead>
                                <tr>
                                    <th>Data</th>
                                    <th>Alimento</th>
                                    <th>Qtd</th>
                                    <th>{user.tipoUsuario === 'DOADOR' ? 'Solicitante' : 'Doador'}</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                {historico.map(item => (
                                    <tr key={item.id}>
                                        <td>{new Date(item.dataCriacao || Date.now()).toLocaleDateString()}</td>
                                        <td><strong>{item.nomeAlimento}</strong></td>
                                        <td>{item.quantidade}</td>
                                        <td>{user.tipoUsuario === 'DOADOR' ? item.nomeReceptor : item.nomeDoador}</td>
                                        <td>
                                            <span className={`badge ${item.status.toLowerCase()}`}>
                                                {item.status}
                                            </span>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    )}
                </div>
            )}
        </div>
    );
}

export default Historico;