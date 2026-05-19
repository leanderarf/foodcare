import React from 'react';
import { Navigate } from 'react-router-dom';

/**
 * Componente de Proteção de Rotas
 * @param {Element} children - O componente da página que será renderizado
 * @param {string} roleRequired - (Opcional) O tipo de usuário necessário para acessar
 */
const PrivateRoute = ({ children, roleRequired }) => {
    // Busca o usuário logado no localStorage (mesma lógica usada no Header)
    const user = JSON.parse(localStorage.getItem('user'));

    // 1. Se não houver usuário logado, redireciona para a página de login
    if (!user) {
        return <Navigate to="/login" />;
    }

    // 2. Se a rota exigir um cargo específico (ex: DOADOR) e o usuário não tiver,
    // redireciona para o Dashboard principal ou Home
    if (roleRequired && user.tipoUsuario !== roleRequired) {
        return <Navigate to="/dashboard" />;
    }

    // 3. Se estiver logado e cumprir os requisitos, renderiza a página solicitada
    return children;
};

export default PrivateRoute;