import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Login() {
    const [formData, setFormData] = useState({
        nome: '',
        senha: ''
    });

    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        const { nome, senha } = formData;

        if (!nome || !senha) {
            alert('Por favor, preencha todos os campos.');
            return;
        }

        const url = 'http://localhost:8080/api/users/login';

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ nome, senha })
        })
        .then(response => response.json())
        .then(data => {
            console.log('Resposta da API:', data);

            if (data.message === 'Usuário está logado') {
                const accountNumber = data.numeroConta;
                if (accountNumber) {
                    localStorage.setItem('accountNumber', accountNumber);
                    navigate('/mainmenu');
                } else {
                    throw new Error('Número da conta não encontrado na resposta.');
                }
            } else {
                alert(data.message || 'Nome de usuário ou senha incorretos.');
            }
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Erro ao logar. Tente novamente mais tarde.');
        });
    };

    const handleCadastro = () => {
        navigate('/cadastro');
    };

    return (
        <>
            <h1>Banco dos CallBoys</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Conta:</label>
                    <input
                        type="text"
                        name="nome"
                        value={formData.nome}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label>Senha:</label>
                    <input
                        type="password"
                        name="senha"
                        value={formData.senha}
                        onChange={handleChange}
                    />
                </div>
                <button type="submit">Entrar</button>
            </form>
            <button onClick={handleCadastro}>Cadastrar</button>
        </>
    );
}

export default Login;
