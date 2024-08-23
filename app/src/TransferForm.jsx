import React, { useState } from 'react';

function TransferForm({ accountNumber, onClose }) {
    const [transferAmount, setTransferAmount] = useState('');
    const [targetAccount, setTargetAccount] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [loading, setLoading] = useState(false);

    const handleTransfer = () => {
        if (!accountNumber) {
            setErrorMessage('Número da conta não disponível.');
            return;
        }

        setLoading(true);
        setErrorMessage('');
        setSuccessMessage('');

        const amount = parseFloat(transferAmount);
        if (isNaN(amount) || amount <= 0) {
            setErrorMessage('Valor da transferência inválido.');
            setLoading(false);
            return;
        }

        const url = 'http://localhost:8080/api/transactions/transferencia';

        const requestData = {
            numeroOrigem: accountNumber,
            numeroDestino: targetAccount,
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
                return response.text().then(text => {
                    throw new Error('Erro na requisição: ' + response.statusText + ' - ' + text);
                });
            }
            return response.json();
        })
        .then(data => {
            setSuccessMessage('Transferência realizada com sucesso!');
            setTransferAmount('');
            setTargetAccount('');
        })
        .catch(error => {
            console.error('Erro:', error);
            setErrorMessage('Erro ao realizar transferência. Tente novamente mais tarde.');
        })
        .finally(() => {
            setLoading(false);
        });
    };

    return (
        <div>
            <h2>Realizar Transferência</h2>
            <div>
                <label>Conta Destino:</label>
                <input
                    type="text"
                    value={targetAccount}
                    onChange={(e) => setTargetAccount(e.target.value)}
                    placeholder="Número da conta destino"
                />
            </div>
            <input
                type="number"
                value={transferAmount}
                onChange={(e) => setTransferAmount(e.target.value)}
                placeholder="Valor da transferência"
                min="0"
            />
            <button onClick={handleTransfer} disabled={loading}>
                {loading ? 'Processando...' : 'Confirmar Transferência'}
            </button>
            {successMessage && <p style={{ color: 'green' }}>{successMessage}</p>}
            {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
            <button onClick={onClose}>Fechar</button>
        </div>
    );
}

export default TransferForm;
