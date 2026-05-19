import React, { useState, useEffect } from 'react';
import api from '../../services/api';
import './Solicitacoes.css';

function Solicitacoes() {
    const [alimentos, setAlimentos] = useState([]);
    const [pedidosRecebidos, setPedidosRecebidos] = useState([]);
    const [loading, setLoading] = useState(true);
    const user = JSON.parse(localStorage.getItem('user'));

    useEffect(() => {
        carregarDados();
    }, []);

    const carregarDados = async () => {
        setLoading(true);
        try {
            if (user.tipoUsuario === 'RECEPTOR') {
                const res = await api.get('/alimento/disponiveis');
                setAlimentos(res.data || []);
            } else {
                const res = await api.get(`/solicitacao/pendentes/${user.id}`);
                setPedidosRecebidos(res.data || []);
            }
        } catch (err) {
            console.error("Erro ao procurar dados", err);
        } finally {
            setLoading(false);
        }
    };

    const handleSolicitar = async (alimento) => {
        const qtdDisponivel = alimento.quantidadeTotal - (alimento.quantidadeReservada || 0);
        const quantidade = prompt(`Quantidade desejada (Máx: ${qtdDisponivel} ${alimento.unidadeMedida}):`);
        
        if (!quantidade || quantidade <= 0 || quantidade > qtdDisponivel) {
            alert("Quantidade inválida ou superior ao stock.");
            return;
        }

        const descricao = prompt("Descreva brevemente a finalidade desta solicitação:");
        if (!descricao) return;

        try {
            const payload = {
                receptorId: user.id,
                quantidade: parseInt(quantidade),
                descricao: descricao,
                alimento: { id: alimento.id }
            };
            await api.post('/solicitacao/criar', payload);
            alert("Solicitação enviada com sucesso!");
            carregarDados();
        } catch (err) {
            alert("Erro ao processar solicitação.");
        }
    };

    const handleDecisao = async (idSolicitacao, acao) => {
        try {
            await api.post(`/solicitacao/${acao}/${user.id}?idSolicitacao=${idSolicitacao}`);
            alert(`Solicitação ${acao === 'aprovar' ? 'aprovada' : 'recusada'}!`);
            carregarDados();
        } catch (err) {
            alert("Erro ao processar decisão.");
        }
    };

    return (
        <div className="solicitacoes-container">
            <header className="page-header">
                <h2>{user.tipoUsuario === 'RECEPTOR' ? 'Alimentos Disponíveis' : 'Solicitações Pendentes'}</h2>
                <p>{user.tipoUsuario === 'RECEPTOR' 
                    ? 'Explore as doações ativas e faça o seu pedido.' 
                    : 'Gerencie os pedidos feitos para os seus itens cadastrados.'}
                </p>
            </header>

            {loading ? <div className="loader">A carregar...</div> : (
                <div className="content-grid">
                    {user.tipoUsuario === 'RECEPTOR' ? (
                        alimentos.map(item => (
                            <div key={item.id} className="item-card">
                                <div className="card-info">
                                    <h3>{item.nome}</h3>
                                    <span className="validade">Validade: {new Date(item.dataValidade).toLocaleDateString()}</span>
                                    <p className="stock">Disponível: <strong>{item.quantidadeTotal - (item.quantidadeReservada || 0)} {item.unidadeMedida}</strong></p>
                                </div>
                                <button onClick={() => handleSolicitar(item)} className="btn-request">Solicitar Item</button>
                            </div>
                        ))
                    ) : (
                        pedidosRecebidos.length === 0 ? <p className="empty-msg">Não há solicitações pendentes no momento.</p> : (
                            pedidosRecebidos.map(sol => (
                                <div key={sol.id} className="sol-card">
                                    <div className="sol-details">
                                        <h4>{sol.nomeAlimento || 'Alimento'}</h4>
                                        <p><strong>Quantidade:</strong> {sol.quantidade}</p>
                                        <p><strong>Motivo:</strong> {sol.descricao}</p>
                                    </div>
                                    <div className="sol-actions">
                                        <button onClick={() => handleDecisao(sol.id, 'aprovar')} className="btn-approve">Aprovar</button>
                                        <button onClick={() => handleDecisao(sol.id, 'recusar')} className="btn-reject">Recusar</button>
                                    </div>
                                </div>
                            ))
                        )
                    )}
                </div>
            )}
        </div>
    );
}

export default Solicitacoes;