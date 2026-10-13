// js/toast.js

const Toast = {
    init() {
        if (!document.getElementById('toast-container')) {
            const container = document.createElement('div');
            container.id = 'toast-container';
            container.style.cssText = `
                position: fixed;
                top: 20px;
                right: 20px;
                z-index: 9999;
                display: flex;
                flex-direction: column;
                gap: 10px;
                width: min(380px, calc(100vw - 40px));
                max-width: 380px;
                pointer-events: none;
            `;
            document.body.appendChild(container);
        }
    },


    show({
             title = 'Notification',
             message = '',
             type = 'info',
             duration = 5000
         }) {

        this.init();
        const container = document.getElementById('toast-container');

        const toast = document.createElement('div');
        toast.style.cssText = `
            pointer-events: auto;
            width: 100%;
            max-width: 100%;
            background: #ffffff;
            border-left: 4px solid ${this._getBorderColor(type)};
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            border-radius: 6px;
            padding: 12px 16px;
            display: flex;
            flex-direction: column;
            gap: 4px;
            font-family: inherit;
            overflow-wrap: anywhere;
            word-break: break-word;
            animation: toastSlideIn 0.3s cubic-bezier(0.16, 1, 0.3, 1);
            transition:
                opacity 0.3s ease,
                transform 0.3s ease;
        `;

        toast.innerHTML = `
            <div style="
                display: flex;
                justify-content: space-between;
                align-items: center;
                gap: 10px;
            ">

                <strong style="
                    font-size: 14px;
                    color: #2d3748;
                    overflow-wrap: anywhere;
                ">
                    ${this._escapeHtml(title)}
                </strong>

                <button
                    aria-label="Close notification"
                    style="
                        background: none;
                        border: none;
                        cursor: pointer;
                        font-size: 16px;
                        color: #a0aec0;
                        line-height: 1;
                        flex-shrink: 0;
                        padding: 2px;
                    "
                    onclick="this.parentElement.parentElement.remove()"
                >
                    ✕
                </button>

            </div>

            <p style="
                margin: 0;
                font-size: 13px;
                color: #4a5568;
                overflow-wrap: anywhere;
                word-break: break-word;
            ">
                ${this._escapeHtml(message)}
            </p>
        `;

        container.appendChild(toast);

        // Auto remove after duration
        setTimeout(() => {
            if (!toast.parentElement) {
                return;
            }
            toast.style.opacity = '0';
            toast.style.transform = 'translateX(30px)';
            setTimeout(() => {
                if (toast.parentElement) {
                    toast.remove();
                }
            }, 300);

        }, duration);
    },

    error(message, title = 'Error Occurred') {
        this.show({ title, message, type: 'error' });
    },

    success(message, title = 'Success') {
        this.show({ title, message, type: 'success' });
    },

    info(message, title = 'Notice') {
        this.show({ title, message, type: 'info' });
    },

    _getBorderColor(type) {
        switch (type) {
            case 'error': return '#e53e3e';   // Red
            case 'success': return '#38a169'; // Green
            case 'warning': return '#dd6b20'; // Orange
            case 'info': return '#d69e2e';    // Yellow
            default: return '#3182ce';        // Blue
        }
    },

    _escapeHtml(str) {
        if (!str) return '';
        return String(str)
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#039;');
    }
};


// =========================================
// TOAST ANIMATION
// =========================================

const style = document.createElement('style');
style.textContent = `
    @keyframes toastSlideIn {
        from {
            transform: translateX(30px);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }


    /* =====================================
       MOBILE TOAST
       ===================================== */

    @media (max-width: 600px) {

        #toast-container {

            top: 12px !important;
            right: 12px !important;
            left: 12px !important;

            width: auto !important;
            max-width: none !important;
        }


        #toast-container > div {

            width: 100% !important;
            max-width: 100% !important;

            padding: 11px 13px !important;

            border-radius: 6px;
        }
    }

`;
document.head.appendChild(style);