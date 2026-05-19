import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import './Cadastro.css';

function Cadastro() {
    const [formData, setFormData] = useState({
        nome: '',
        email: '',
        senha: '',
        tipoUsuario: 'RECEPTOR', // Valor padrão
        cpfCnpj: '',
        telefone: ''
    });

    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        // Simulação de cadastro - Em produção, aqui seria a chamada ao axios.post para o backend
        console.log("Dados cadastrados:", formData);
        alert("Cadastro realizado com sucesso!");
        navigate('/login');
    };

    return (
        <div className="cadastro-container">
            <div className="cadastro-box">
                <h2>Crie sua conta no <span>FoodCare</span></h2>
                <p>Escolha seu perfil e junte-se à nossa rede de solidariedade.</p>

                <form onSubmit={handleSubmit}>
                    <div className="input-group">
                        <label>Nome Completo / Instituição</label>
                        <input 
                            type="text" name="nome" placeholder="Digite seu nome" 
                            onChange={handleChange} required 
                        />
                    </div>

                    <div className="input-group">
                        <label>E-mail</label>
                        <input 
                            type="email" name="email" placeholder="exemplo@email.com" 
                            onChange={handleChange} required 
                        />
                    </div>

                    <div className="row">
                        <div className="input-group">
                            <label>Tipo de Perfil</label>
                            <select name="tipoUsuario" onChange={handleChange}>
                                <option value="RECEPTOR">Instituição / Receptor</option>
                                <option value="DOADOR">Empresa / Doador</option>
                            </select>
                        </div>
                        <div className="input-group">
                            <label>CPF ou CNPJ</label>
                            <input 
                                type="text" name="cpfCnpj" placeholder="000.000.000-00" 
                                onChange={handleChange} required 
                            />
                        </div>
                    </div>

                    <div className="input-group">
                        <label>Senha</label>
                        <input 
                            type="password" name="senha" placeholder="********" 
                            onChange={handleChange} required 
                        />
                    </div>

                    <button type="submit" className="btn-registrar">Finalizar Cadastro</button>
                </form>

                <p className="footer-link">
                    Já possui uma conta? <Link to="/login">Faça login</Link>
                </p>
            </div>
        </div>
    );
}

export default Cadastro;