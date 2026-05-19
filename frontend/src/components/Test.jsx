import React, { useState } from "react";
import { useAuth } from "../providers/AuthProvider";
export default function Test({ userId }) {
  const [file, setFile] = useState(null);
  const [progress, setProgress] = useState(0);
  const {token,setToken,userDetails}=useAuth();
  async function handleUpload() {
    if (!file) return alert("Select a file");

    // 1) ask backend for presigned URL
    const presignResp = await fetch("http://localhost:8080/presign", {
      method: "POST",
      headers: { "Content-Type": "application/json" ,
        "Authorization":`Bearer ${token}`
      },
      body: JSON.stringify({
        filename: file.name,
        contentType: file.type || "application/octet-stream",
        username:"ganesh",
        category:"profilePic"
      })
    });
    const data = await presignResp.json();
    const url = data.url;
    const requiredHeaders = data.headers || {};

    // 2) upload directly to R2 using PUT
    await new Promise((resolve, reject) => {
      const xhr = new XMLHttpRequest();
      xhr.open("PUT", url, true);
      // set required headers
      Object.entries(requiredHeaders).forEach(([k, v]) => xhr.setRequestHeader(k, v));

      xhr.upload.onprogress = (evt) => {
        if (evt.lengthComputable) {
          const percent = Math.round((evt.loaded / evt.total) * 100);
          setProgress(percent);
        }
      };

      xhr.onload = () => {
        if (xhr.status >= 200 && xhr.status < 300) {
          resolve(xhr.response);
        } else {
          reject(new Error("Upload failed: " + xhr.status + " " + xhr.response));
        }
      };
      xhr.onerror = () => reject(new Error("Network error during upload"));
      xhr.send(file);
    });

    alert("Upload complete!");
  }

  return (
    <div>
      <input type="file" onChange={e => setFile(e.target.files?.[0])} />
      <button onClick={handleUpload}>Upload</button>
      <div>Progress: {progress}%</div>
    </div>
  );
}
