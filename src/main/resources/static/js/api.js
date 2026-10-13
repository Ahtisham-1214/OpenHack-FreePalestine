// js/api.js
const API_BASE_URL = window.location.origin;


// Helper to read cookies (specifically the CSRF token)
function getCookie(name) {
    let cookieValue = null;
    if (document.cookie && document.cookie !== '') {
        const cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i].trim();
            if (cookie.substring(0, name.length + 1) === (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
}


class ApiError extends Error {
    constructor(errorResponse, status) {
        // Use backend message, fallback to generic error title, or status text
        let fallbackMsg = errorResponse?.error || `HTTP Error ${status}`;
        let message = errorResponse?.message || fallbackMsg;

        if (status === 403) {
            // If the backend didn't provide a specific message, or just said "Forbidden"
            if (!errorResponse?.message || errorResponse.message === 'Forbidden') {
                message = "Access Denied";
            }
            fallbackMsg = "Forbidden"; // Changes the title of the toast
        }

        super(message);
        this.name = 'ApiError';
        this.status = status || errorResponse?.status;
        this.error = errorResponse?.error || 'Request Failed';
        this.className = errorResponse?.className || null;
        this.timestamp = errorResponse?.timestamp || null;
    }
}

async function apiRequest(endpoint, options = {}) {
    const isSilent = options.silent || false;
    // 1. Define default headers
    const headers = {
        'Accept': 'application/json',
        'X-Requested-With': 'XMLHttpRequest', // <--- Added this to trigger the 401 on the backend
        ...options.headers
    };

    // 2. Automatically attach CSRF token for unsafe HTTP methods
    const method = (options.method || 'GET').toUpperCase();
    if (['POST', 'PUT', 'PATCH', 'DELETE'].includes(method)) {
        const csrfToken = getCookie('XSRF-TOKEN');
        if (csrfToken) {
            headers['X-XSRF-TOKEN'] = csrfToken;
        }
    }

    // 3. Set 'Content-Type' to JSON ONLY if body is NOT FormData
    if (options.body && !(options.body instanceof FormData)) {
        if (!headers['Content-Type']) {
            headers['Content-Type'] = 'application/json';
        }
    } else if (options.body instanceof FormData) {
        // Explicitly remove Content-Type so browser generates boundary string
        delete headers['Content-Type'];
    }

    const config = {
        ...options,
        headers
    };

    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, config);

        // --- NEW: Handle Session Timeout ---
        if (response.status === 401) {
            if (!isSilent) {
                Toast.error("Your session has expired. Redirecting to login...", "Session Expired");
                setTimeout(() => {
                    window.location.href = '/login.html';
                }, 1500); // Give the user 1.5 seconds to read the toast before redirecting
            }
            // Return a null or throw to stop further execution in the calling function
            throw new Error("Session Expired");
        }

        if (response.status === 204) return null; // No Content

        // Attempt to parse response JSON body
        const data = await response.json().catch(() => null);

        if (!response.ok) {
            // Instantiate typed error using Spring Boot ErrorResponse payload
            const apiError = new ApiError(data, response.status);

            // Display rich toast directly
            if (!isSilent) {
            Toast.error(
                apiError.message,
                `${apiError.error} (${apiError.status})`
            );
            }

            throw apiError;
        }

        return data;

    } catch (error) {
        // Handle network drops or backend server unreachability (Fetch failed)
        if (error.name === 'TypeError' && error.message.includes('fetch')) {
            const networkMsg = 'Unable to connect to the backend server. Please verify your connection.';
            Toast.error(networkMsg, 'Network Error');
            console.error(`[Network Error ${endpoint}]:`, error);
            throw new Error(networkMsg);
        }

        // Prevent logging the 401 "Session Expired" as a standard error
        if (error.message !== "Session Expired") {
            console.error(`[API Error ${endpoint}]:`, error);
        }
        throw error;
    }
}