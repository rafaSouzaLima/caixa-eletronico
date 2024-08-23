import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import DepositForm from './DepositForm';
import WithdrawForm from './WithdrawForm';
import TransferForm from './TransferForm';

function MainMenu() {
    const [transactions, setTransactions] = useState([]);
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const [fetchAttempted, setFetchAttempted] = useState(false);
    const [showDepositForm, setShowDepositForm] = useState(false);
    const [showWithdrawForm, setShowWithdrawForm] = useState(false);
    const [showTransferForm, setShowTransferForm] = useState(false);

    const accountNumber = localStorage.getItem('accountNumber');
    const navigate = useNavigate();

    useEffect(() => {
        if (!accountNumber) {
            console.log('Sem número da conta');
            setError('Número da conta não disponível.');
        }
    }, [accountNumber]);

    const handleExtrato = () => {
        if (!accountNumber) {
            setError('Número da conta não disponível.');
            return;
        }

        setLoading(true);
        setError('');
        setFetchAttempted(true);

        const url = `http://localhost:8080/api/transactions/extrato/${accountNumber}`;

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => {
                        throw new Error('Erro na requisição: ' + response.statusText + ' - ' + text);
                    });
                }
                return response.json();
            })
            .then(data => {
                if (data.length === 0) {
                    setError('Nenhuma transação encontrada.');
                } else {
                    setTransactions(data);
                    setError('');
                }
            })
            .catch(error => {
                console.error('Erro:', error);
                setError('Erro ao buscar extrato. Tente novamente mais tarde.');
            })
            .finally(() => {
                setLoading(false);
            });
    };

    const toggleDepositForm = () => {
        setShowDepositForm(prev => !prev);
    };

    const toggleWithdrawForm = () => {
        setShowWithdrawForm(prev => !prev);
    };

    const toggleTransferForm = () => {
        setShowTransferForm(prev => !prev);
    };

    const handleLogout = () => {
        localStorage.removeItem('accountNumber');
        navigate('/');
    };

    return (
        <>
            <h1>Menu Principal</h1>
            <div>
                <button onClick={handleExtrato}>Extrato</button>
                <button onClick={toggleDepositForm}>
                    {showDepositForm ? 'Cancelar Depósito' : 'Depósito'}
                </button>
                <button onClick={toggleWithdrawForm}>
                    {showWithdrawForm ? 'Cancelar Saque' : 'Saque'}
                </button>
                <button onClick={toggleTransferForm}>
                    {showTransferForm ? 'Cancelar Transferência' : 'Transferência'}
                </button>
                <button onClick={handleLogout}>Sair</button>
            </div>

            {loading && <p>Carregando...</p>}

            {fetchAttempted && error && <p style={{ color: 'red' }}>{error}</p>}
            {!loading && transactions.length > 0 && (
                <div>
                    <h2>Transações</h2>
                    <ul>
                        {transactions.map((transaction, index) => (
                            <li key={index}>
                                {transaction.data} - {transaction.tipoMovimentacao} - R${transaction.valor} - R${transaction.saldoFinal}
                            </li>
                        ))}
                    </ul>
                </div>
            )}
            {!loading && transactions.length === 0 && !error && (
                <p>Não há transações para exibir.</p>
            )}

            {showDepositForm && (
                <DepositForm
                    accountNumber={accountNumber}
                    onClose={toggleDepositForm}
                />
            )}

            {showWithdrawForm && (
                <WithdrawForm
                    accountNumber={accountNumber}
                    onClose={toggleWithdrawForm}
                />
            )}

            {showTransferForm && (
                <TransferForm
                    accountNumber={accountNumber}
                    onClose={toggleTransferForm}
                />
            )}
        </>
    );
}

export default MainMenu;
