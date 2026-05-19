import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Home.css';

function Home() {
    const navigate = useNavigate();

    return (
        <div className="home-container">
            {/* HERO SECTION */}
            <section className="hero-section">
                <div className="hero-content">
                    <h1>Food<span>Care</span></h1>
                    <h2>Reduzindo desperdícios. Alimentando vidas.</h2>
                    <p>
                        Uma plataforma inteligente que conecta doadores, mercados e instituições 
                        para redistribuir alimentos próximos ao vencimento de forma rápida, organizada e segura.
                    </p>
                    <div className="hero-buttons">
                        <button onClick={() => navigate('/login')} className="btn-hero-primary">Entrar na Plataforma</button>
                        <button onClick={() => navigate('/cadastro')} className="btn-hero-secondary">Começar Agora</button>
                    </div>
                </div>
                <div className="hero-image">
                    <img src="/images/hero-food.jpg" alt="Solidariedade e organização de alimentos" />
                </div>
            </section>

            {/* SEÇÃO: POR QUE EXISTIMOS */}
            <section className="purpose-section">
                <div className="purpose-text">
                    <h2>Combater o desperdício também é cuidar de pessoas</h2>
                    <p>
                        Milhares de alimentos ainda próprios para consumo são descartados diariamente, 
                        enquanto comunidades e instituições enfrentam insegurança alimentar.
                    </p>
                    <p className="highlight">
                        O FoodCare nasceu para conectar quem pode doar com quem realmente precisa receber.
                    </p>
                </div>
                <div className="purpose-image">
                    <img src="/images/impacto-social.jpg" alt="Impacto do FoodCare" />                </div>
            </section>

            {/* FEATURE CARDS */}
            <section className="features-section">
                <div className="feature-card">
                    <div className="icon-box">🍎</div>
                    <h3>Para Doadores</h3>
                    <p>Cadastre alimentos próximos do vencimento, gerencie solicitações e contribua para reduzir o desperdício alimentar de forma organizada e segura.</p>
                </div>
                <div className="feature-card">
                    <div className="icon-box">🤝</div>
                    <h3>Para Receptores</h3>
                    <p>Encontre alimentos disponíveis, realize solicitações e acompanhe o processo de doação em tempo real pela plataforma.</p>
                </div>
            </section>

            {/* COMO FUNCIONA */}
            <section className="how-it-works">
                <h2>Como funciona?</h2>
                <div className="steps-grid">
                    <div className="step-item">
                        <span className="step-number">01</span>
                        <h4>Cadastro de alimentos</h4>
                        <p>Doadores registram alimentos disponíveis para doação com data de validade e quantidade.</p>
                    </div>
                    <div className="step-item">
                        <span className="step-number">02</span>
                        <h4>Solicitações</h4>
                        <p>Instituições e receptores visualizam o estoque disponível e realizam pedidos diretos.</p>
                    </div>
                    <div className="step-item">
                        <span className="step-number">03</span>
                        <h4>Aprovação e retirada</h4>
                        <p>O sistema organiza o fluxo de aprovação e garante uma retirada segura e organizada.</p>
                    </div>
                </div>
            </section>

            {/* IMPACTO SOCIAL */}
            <section className="social-impact">
                <h2>Impacto Social</h2>
                <div className="impact-cards">
                    <div className="impact-mini-card">
                        <h4>Redução do desperdício</h4>
                        <p>Evita o descarte de alimentos ainda próprios para consumo.</p>
                    </div>
                    <div className="impact-mini-card">
                        <h4>Combate à insegurança</h4>
                        <p>Facilita o acesso de comunidades a alimentos disponíveis.</p>
                    </div>
                    <div className="impact-mini-card">
                        <h4>Rastreabilidade</h4>
                        <p>Todo o fluxo de doação é registrado e controlado pelo sistema.</p>
                    </div>
                </div>
            </section>
        </div>
    );
}

export default Home;