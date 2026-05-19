import React from 'react';
import './Footer.css';

function Footer() {
    const currentYear = new Date().getFullYear();

    return (
        <footer className="footer-container">
            <div className="footer-content">
                <div className="footer-section">
                    <h3 className="footer-logo">Food<span>Care</span></h3>
                    <p>Transformando o excedente em solidariedade.</p>
                </div>

                <div className="footer-section">
                    <h4>Links Úteis</h4>
                    <ul>
                        <li><a href="/sobre">Sobre o Projeto</a></li>
                        <li><a href="/contato">Contato</a></li>
                        <li><a href="/termos">Termos de Uso</a></li>
                    </ul>
                </div>

                <div className="footer-section">
                    <h4>Projeto de Extensão</h4>
                    <p>UNIP - Universidade Paulista</p>
                    <p>Análise e Desenvolvimento de Sistemas</p>
                </div>
            </div>
            
            <div className="footer-bottom">
                <p>&copy; {currentYear} Food Care - Todos os direitos reservados.</p>
            </div>
        </footer>
    );
}

export default Footer;