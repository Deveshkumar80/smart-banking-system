import React, { useEffect, useState } from "react";

function Dashboard() {
  const [account, setAccount] = useState("");
  const [balance, setBalance] = useState(0);
  const [transactions, setTransactions] = useState([]);

  useEffect(() => {
    const user = localStorage.getItem("user");
    if (user) {
      setAccount(user);
      fetchBalance(user);
    }
  }, []);

  const fetchBalance = async (acc) => {
    const res = await fetch(`http://localhost:8080/api/account/${acc}`);
    const data = await res.json();
    setBalance(data.balance);
  };

  // 💰 Deposit
  const handleDeposit = async () => {
    const amount = prompt("Enter amount:");
    if (!amount) return;

    const res = await fetch(
      `http://localhost:8080/api/account/deposit?accountNumber=${account}&amount=${amount}`,
      { method: "POST" }
    );

    const data = await res.json();
    setBalance(data.balance);
  };

  // 💸 Withdraw
  const handleWithdraw = async () => {
    const amount = prompt("Enter amount:");
    if (!amount) return;

    const res = await fetch(
      `http://localhost:8080/api/account/withdraw?accountNumber=${account}&amount=${amount}`,
      { method: "POST" }
    );

    const data = await res.json();
    setBalance(data.balance);
  };

  // 🔁 Transfer
  const handleTransfer = async () => {
    const toAccount = prompt("Enter receiver account:");
    const amount = prompt("Enter amount:");

    if (!toAccount || !amount) return;

    const res = await fetch(
      `http://localhost:8080/api/account/transfer?fromAccount=${account}&toAccount=${toAccount}&amount=${amount}`,
      { method: "POST" }
    );

    const data = await res.json();
    setBalance(data.balance);
  };

  // 📜 HISTORY (FIXED)
  const handleHistory = async () => {
    const res = await fetch(
      `http://localhost:8080/api/account/transactions/${account}`
    );

    const data = await res.json();
    setTransactions(data); // ✅ show on screen
  };

  return (
    <div style={container}>
      <h1>Welcome {account} 🎉</h1>

      <div style={balanceCard}>
        <h2>Current Balance</h2>
        <h1>₹ {balance}</h1>
      </div>

      <div style={{ marginTop: "20px" }}>
        <button style={btn} onClick={handleDeposit}>Deposit</button>
        <button style={btn} onClick={handleWithdraw}>Withdraw</button>
        <button style={btn} onClick={handleTransfer}>Transfer</button>
        <button style={btn} onClick={handleHistory}>Transaction History</button>
      </div>

      {/* 📊 TABLE */}
      {transactions.length > 0 && (
        <table style={table}>
          <thead>
            <tr>
              <th>Type</th>
              <th>Amount</th>
              <th>Date</th>
            </tr>
          </thead>
          <tbody>
            {transactions.map((tx, i) => (
              <tr key={i}>
                <td>{tx.type}</td>
                <td>₹ {tx.amount}</td>
                <td>{tx.date}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

// 🎨 styles
const container = {
  padding: "40px",
  fontFamily: "Segoe UI",
};

const balanceCard = {
  marginTop: "20px",
  padding: "20px",
  borderRadius: "12px",
  background: "#f4f6f8",
  width: "300px",
};

const btn = {
  margin: "10px",
  padding: "10px 15px",
  background: "#2c3e50",
  color: "#fff",
  border: "none",
  borderRadius: "8px",
};

const table = {
  marginTop: "30px",
  width: "80%",
  background: "#fff",
  borderCollapse: "collapse",
};

export default Dashboard;