import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Login.css";

function Login() {
  const [account, setAccount] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  // 🔐 LOGIN FUNCTION (CONNECTED TO BACKEND)
  const handleLogin = async () => {

    // ✅ Validation
    if (!account || !password) {
      alert("Enter account number and password");
      return;
    }

    try {
      const res = await fetch("http://localhost:8080/api/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          accountNumber: account,
          password: password,
        }),
      });

      const data = await res.json();

      // ✅ SUCCESS
      if (data.status === "success") {
        localStorage.setItem("user", data.accountNumber);
        navigate("/dashboard");
      } 
      // ❌ FAILURE
      else {
        alert(data.message);
      }

    } catch (err) {
      alert("Server error. Try again.");
    }
  };

  return (
    <div className="page">

      {/* Navbar */}
      <div className="navbar">
        <div className="logo">
          SWISS BANK <span>🇨🇭</span>
        </div>

        <div className="nav-links">
          <div className="contact">
            Contact
            <div className="contact-dropdown">
              <p>Devesh Kumar - 9472009630</p>
              <p>Khushi Negi - 8057622277</p>
              <p>Kritika - 7837535246</p>
            </div>
          </div>
        </div>
      </div>

      {/* Main Card */}
      <div className="card">

        {/* Left Section */}
        <div className="left">
          <h1>Secure Access Portal</h1>
          <p>
            Please enter your credentials to access your banking dashboard.
          </p>

          {/* News */}
          <div className="news-box">
            <h3>Market Updates</h3>
            <div className="news-scroll">
              <p>
                📈 Nifty rises 120 pts &nbsp; | &nbsp;
                💰 Gold prices increase &nbsp; | &nbsp;
                📉 Sensex slightly down &nbsp; | &nbsp;
                🏦 RBI announces new policy &nbsp; | &nbsp;
                📊 Banking stocks surge today
              </p>
            </div>
          </div>
        </div>

        {/* Right Section */}
        <div className="right">
          <div className="login-box">

            <h3>Login</h3>

            <input
              type="text"
              placeholder="Account Number"
              value={account}
              onChange={(e) => setAccount(e.target.value)}
            />

            <input
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />

            <button onClick={handleLogin}>Sign In</button>

          </div>
        </div>

      </div>

    </div>
  );
}

export default Login;