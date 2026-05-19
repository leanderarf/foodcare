import React, { useState, useEffect } from 'react';
import api from '../../services/api';
import './GerenciamentoAlimentos.css';

function GerenciamentoAlimentos() {
    const [meusAlimentos, setMeusAlimentos] = useState([]);
    const [loading, setLoading] = useState(true);
    const [novoAlimento, setNovoAlimento] = useState({
        nome: '',
        dataValidade: '',
        quantidadeTotal: '',
        unidadeMedida: 'KG'
    });

    const user = JSON.parse(localStorage.getItem('user'));

    useEffect(() => {
        carregarMeusAlimentos();
    }, []);

    const carregarMeusAlimentos = async () => {
        try {
            // Rota para buscar alimentos cadastrados por este doador específico
            const response = await api.get(`/alimento/doador/${user.id}`);
            setMeusAlimentos(response.data || []);
        } catch (err) {
            console.error("Erro ao carregar seus alimentos", err);
        } finally {
            setLoading(false);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNovoAlimento({ ...novoAlimento, [name]: value });
    };

    const handleCadastrar = async (e) => {
        e.preventDefault();
        try {
            await api.post(`/alimento/registrar/${user.id}`, novoAlimento);
            alert("Alimento registado com sucesso para doação!");
            setNovoAlimento({ nome: '', dataValidade: '', quantidadeTotal: '', unidadeMedida: 'KG' });
            carregarMeusAlimentos();
        } catch (err) {
            alert("Erro ao registar alimento.");
        }
    };

    return (
        <div className="gerenciamento-container">
            <section className="cadastro-section">
                <h2>Registar Novo Alimento</h2>
                <form className="alimento-form" onSubmit={handleCadastrar}>
                    <div className="form-group">
                        <label>Nome do Alimento</label>
                        <input 
                            type="text" name="nome" value={novoAlimento.nome}
                            onChange={handleInputChange} placeholder="Ex: Arroz Tipo 1" required 
                        />
                    </div>
                    <div className="form-row">
                        <div className="form-group">
                            <label>Data de Validade</label>
                            <input 
                                type="date" name="dataValidade" value={novoAlimento.dataValidade}
                                onChange={handleInputChange} required 
                            />
                        </div>
                        <div className="form-group">
                            <label>Quantidade</label>
                            <input 
                                type="number" name="quantidadeTotal" value={novoAlimento.quantidadeTotal}
                                onChange={handleInputChange} placeholder="0" required 
                            />
                        </div>
                        <div className="form-group">
                            <label>Unidade</label>
                            <select name="unidadeMedida" value={novoAlimento.unidadeMedida} onChange={handleInputChange}>
                                <option value="KG">Quilos (KG)</option>
                                <option value="UN">Unidades (UN)</option>
                                <option value="LT">Litros (LT)</option>
                            </select>
                        </div>
                    </div>
                    <button type="submit" className="btn-save">Disponibilizar para Doação</button>
                </form>
            </section>

            <section className="listagem-section">
                <h2>Meus Alimentos Disponibilizados</h2>
                {loading ? <p>A carregar...</p> : (
                    <div className="table-responsive">
                        <table className="alimentos-table">
                            <thead>
                                <tr>
                                    <th>Alimento</th>
                                    <th>Validade</th>
                                    <th>Total</th>
                                    <th>Reservado</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                {meusAlimentos.map(item => (
                                    <tr key={item.id}>
                                        <td>{item.nome}</td>
                                        <td>{new Date(item.dataValidade).toLocaleDateString()}</td>
                                        <td>{item.quantidadeTotal} {item.unidadeMedida}</td>
                                        <td>{item.quantidadeReservada || 0}</td>
                                        <td>
                                            <span className={`status-pill ${item.quantidadeTotal > 0 ? 'active' : 'exhausted'}`}>
                                                {item.quantidadeTotal > 0 ? 'Disponível' : 'Esgotado'}
                                            </span>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                )}
            </section>
        </div>
    );
}

export default GerenciamentoAlimentos;