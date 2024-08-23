import React, { useState } from 'react';

function DepositForm({ accountNumber, onClose }) {
    console.log('Número da conta recebido no DepositForm:', accountNumber); // teste debugando

    const [depositAmount, setDepositAmount] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [loading, setLoading] = useState(false);

    const handleDeposit = () => {
        if (!accountNumber) {
            setErrorMessage('Número da conta não disponível.');
            return;
        }

        setLoading(true);
        setErrorMessage('');
        setSuccessMessage('');

        const amount = parseFloat(depositAmount);
        if (isNaN(amount) || amount <= 0) {
            setErrorMessage('Valor do depósito inválido.');
            setLoading(false);
            return;
        }

        const url = 'http://localhost:8080/api/transactions/deposito';

        const requestData = {
            numero: accountNumber,
            valor: amount
        };

        console.log('Dados da requisição:', requestData); // tentando debugar

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        })
        .then(response => {
            const contentType = response.headers.get('Content-Type');
            if (contentType && contentType.includes('application/json')) {
                return response.json();
            } else {
                return response.text().then(text => {
                    throw new Error('Resposta não é JSON: ' + text);
                });
            }
        })
        .then(data => {
            if (data.message) {
                setSuccessMessage(data.message);
                setDepositAmount('');
            } else {
                throw new Error('Mensagem não encontrada na resposta.');
            }
        })
        .catch(error => {
            console.error('Erro:', error);
            setErrorMessage('Erro ao realizar depósito. Tente novamente mais tarde.');
        })
        .finally(() => {
            setLoading(false);
        });
    };

    return (
        <div>
            <h2>Realizar Depósito</h2>
            <input
                type="number"
                value={depositAmount}
                onChange={(e) => setDepositAmount(e.target.value)}
                placeholder="Valor do depósito"
                min="0"
            />
            <button onClick={handleDeposit} disabled={loading}>
                {loading ? 'Processando...' : 'Confirmar Depósito'}
            </button>
            {successMessage && <p style={{ color: 'green' }}>{successMessage}</p>}
            {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
            <button onClick={onClose}>Fechar</button>
        </div>
    );
}

export default DepositForm;
