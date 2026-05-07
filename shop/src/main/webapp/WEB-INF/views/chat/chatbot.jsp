<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AI 챗봇</title>
    <link href="${path}/css/chatbot.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@latest/tabler-icons.min.css">
</head>
<body>

<div class="chat-wrapper">
    <div class="chat-header">
        <div class="bot-avatar">🌸</div>
        <div class="chat-header-text">
            <div class="chat-header-title">AI 챗봇</div>
            <div class="chat-header-sub">지금 온라인이에요</div>
        </div>
        <div class="online-dot"></div>
    </div>

    <div class="chat-messages" id="chatMessages">
        <div class="msg-row bot">
            <div class="avatar">🌸</div>
            <div>
                <div class="bubble">AI 챗봇입니다. 궁금하신 점을 문의해 주세요! ✨</div>
                <div class="msg-time">방금 전</div>
            </div>
        </div>
    </div>

    <div id="typingRow" class="msg-row bot typing-row" style="display:none;">
        <div class="avatar">🌸</div>
        <div class="typing-indicator">
            <span></span><span></span><span></span>
        </div>
    </div>

    <div class="chat-input-area">
        <textarea id="gpt_question" rows="1" placeholder="무엇이든 물어보세요 🌷" oninput="autoResize(this)"></textarea>
        <button class="send-btn" onclick="gptquestion()" title="전송">
            <i class="ti ti-send"></i>
        </button>
    </div>
</div>

<script type="text/javascript">
    const textarea = document.getElementById('gpt_question');
    const messages = document.getElementById('chatMessages');
    const typingRow = document.getElementById('typingRow');

    function autoResize(el) {
        el.style.height = 'auto';
        el.style.height = Math.min(el.scrollHeight, 90) + 'px';
    }

    function now() {
        return new Date().toLocaleTimeString('ko-KR', {hour: '2-digit', minute: '2-digit'});
    }

    function addMessage(text, who) {
        const row = document.createElement('div');
        row.className = 'msg-row ' + who;
        const ico = who === 'bot' ? '🌸' : '🦋';
        row.innerHTML =
            "<div class='avatar'>" + ico + "</div>" +
            "<div><div class='bubble'>" + text + "</div>" +
            "<div class='msg-time'>" + now() + "</div></div>";
        messages.appendChild(row);
        messages.scrollTop = messages.scrollHeight;
    }

    textarea.addEventListener('keydown', function (e) {
        if (e.keyCode === 13 && !e.shiftKey) {
            e.preventDefault();
            gptquestion();
        }
    });

    function gptquestion() {
        var question = document.querySelector('#gpt_question').value.trim();
        if (question === '') {
            alert('AI챗봇에게 질문할 내용을 입력해 주세요');
            textarea.focus();
            return;
        }
        if (question.length < 10) {
            alert('좀 더 자세히 입력해 주세요 🌸');
            textarea.focus();
            return;
        }

        addMessage(question.replace(/\n/g, '<br>'), 'me');
        textarea.value = '';
        textarea.style.height = 'auto';

        typingRow.style.display = 'flex';
        messages.scrollTop = messages.scrollHeight;

        var paramdata = {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
            cache: 'no-cache',
            referrerPolicy: 'no-referrer',
            body: 'question=' + encodeURIComponent(question)
        };

        fetch('${path}/ajax/gptquestion', paramdata)
            .then(function (response) {
                return response.text();
            })
            .then(function (gptdata) {
                typingRow.style.display = 'none';
                addMessage(gptdata.replaceAll('\n', '<br>').replaceAll(' ', '&nbsp;'), 'bot');
            })
            .catch(function () {
                typingRow.style.display = 'none';
                addMessage('잠시 후 다시 시도해 주세요 🌷', 'bot');
            });
    }
</script>

</body>
</html>
