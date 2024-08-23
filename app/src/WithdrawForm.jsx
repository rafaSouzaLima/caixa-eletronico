import React, { useState } from 'react';

function WithdrawForm({ accountNumber, onClose }) {
    const [withdrawAmount, setWithdrawAmount] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [loading, setLoading] = useState(false);

    const handleWithdraw = () => {
        if (!accountNumber) {
            setErrorMessage('Número da conta não disponível.');
            return;
        }

        setLoading(true);
        setErrorMessage('');
        setSuccessMessage('');

        const amount = parseFloat(withdrawAmount);
        if (isNaN(amount) || amount <= 0) {
            setErrorMessage('Valor do saque inválido.');
            setLoading(false);
            return;
        }

        const url = 'http://localhost:8080/api/transactions/saque';

        const requestData = {
            numero: accountNumber,
            valor: amount
        };

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        })
        .then(response => {
            if (!response.ok) {
                return response.json().then(data => {
                    throw new Error(data.message || 'Erro na requisição');
                });
            }
            return response.json();
        })
        .then(data => {
            setSuccessMessage(data.message || 'Saque realizado com sucesso!');
            setWithdrawAmount('');
        })
        .catch(error => {
            console.error('Erro:', error);
            setErrorMessage(error.message || 'Erro ao realizar saque. Tente novamente mais tarde.');
        })
        .finally(() => {
            setLoading(false);
        });
    };

    return (
        <div>
            <h2>Realizar Saque</h2>
            <input
                type="number"
                value={withdrawAmount}
                onChange={(e) => setWithdrawAmount(e.target.value)}
                placeholder="Valor do saque"
                min="0"
            />
            <button onClick={handleWithdraw} disabled={loading}>
                {loading ? 'Processando...' : 'Confirmar Saque'}
            </button>
            {successMessage && <p style={{ color: 'green' }}>{successMessage}</p>}
            {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
            <button onClick={onClose}>Fechar</button>
        </div>
    );
}

export default WithdrawForm;
