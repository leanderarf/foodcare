import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import React from 'react';

// Importação de Componentes Globais
import Header from './components/Header/Header';
import Footer from './components/Footer/Footer';
import PrivateRoute from './components/PrivateRoute';

// Importação de Páginas
import Login from './pages/Login/Login';
import Dashboard from './pages/Dashboard/Dashboard';
import Home from './pages/Home/Home';
import Cadastro from './pages/Cadastro/Cadastro';
import GerenciamentoAlimentos from './pages/GerenciamentoAlimentos/GerenciamentoAlimentos';
import Solicitacoes from './pages/Solicitacoes/Solicitacoes';
import Historico from './pages/Historico/Historico';

function App() {
  return (
    <Router>
      <div style={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
        <Header />
        
        <main style={{ flex: '1', padding: '80px' }}>
          <Routes>
            {/* Rotas Públicas */}
            <Route path="/" element={<Home />} /> 
            <Route path="/login" element={<Login />} />
            <Route path="/cadastro" element={<Cadastro />} />

            {/* Rotas Privadas (Protegidas pelo PrivateRoute) */}
            <Route 
              path="/dashboard" 
              element={
                <PrivateRoute>
                  <Dashboard />
                </PrivateRoute>
              } 
            />

            <Route 
              path="/solicitacoes" 
              element={
                <PrivateRoute>
                  <Solicitacoes />
                </PrivateRoute>
              } 
            />

            <Route 
              path="/historico" 
              element={
                <PrivateRoute>
                  <Historico />
                </PrivateRoute>
              } 
            />

            {/* Rota Restrita a DOADORES */}
            <Route 
              path="/gerenciamento-alimentos" 
              element={
                <PrivateRoute roleRequired="DOADOR">
                  <GerenciamentoAlimentos />
                </PrivateRoute>
              } 
            /> 
            

            {/* Redireciona qualquer rota desconhecida para o login ou home */}
            <Route path="*" element={<Navigate to="/" />} />
          </Routes>
        </main>

        <Footer />
      </div>
    </Router>
  );
}

export default App;