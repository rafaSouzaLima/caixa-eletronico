import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Cadastro() {
    const [formData, setFormData] = useState({
        nome: '',
        senha: '',
        saldo: ''
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

        const dados = {
            nome: formData.nome,
            senha: formData.senha,
            conta: {
                saldo: parseFloat(formData.saldo)
            }
        };

        const url = 'http://localhost:8080/api/users/register';

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dados)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro na requisição: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log('Sucesso:', data);

            setFormData({
                nome: '',
                senha: '',
                saldo: ''
            });

            alert(`Cadastro realizado com sucesso! Número da conta: ${data.conta.numero}`);

            navigate('/');
        })
        .catch(error => {
            console.error('Erro:', error);
        });
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>Nome:</label>
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
            <div>
                <label>Saldo:</label>
                <input
                    type="number"
                    name="saldo"
                    value={formData.saldo}
                    onChange={handleChange}
                />
            </div>
            <button type="submit">Enviar</button>
        </form>
    );
}

export default Cadastro;
