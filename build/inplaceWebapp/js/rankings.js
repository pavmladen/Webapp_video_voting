let currentPage = 1;
const perPage = 20;
let videos = [];

window.addEventListener("DOMContentLoaded", () => {
    fetch("/api/videos/top")
        .then(res => res.json())
        .then(data => {
            videos = data;
            const z = 1.96;

            function ciLowerBound(pos, n, z) {
                if (n === 0) return 0;
                const phat = pos / n;
                return (
                    (phat + z * z / (2 * n) - z * Math.sqrt((phat * (1 - phat) + z * z / (4 * n)) / n)) /
                    (1 + z * z / n)
                );
            }

            videos.forEach(v => {
                v.rankScore = ciLowerBound(v.positiveVotes, v.totalVotes, z);
            });

            videos.sort((a, b) => b.rankScore - a.rankScore);

            renderPage(currentPage);
            renderPagination();
        })
        .catch(err => console.error("Failed to load rankings:", err));

        
});

function renderPage(page) {
    const tbody = document.querySelector("table tbody");
    tbody.innerHTML = "";

    const start = (page - 1) * perPage;
    const paginated = videos.slice(start, start + perPage);

    paginated.forEach((video, index) => {
        const tr = document.createElement("tr");
        const thumbnailSrc = video.thumbnailUrl.startsWith("http")
            ? video.thumbnailUrl
            : `${window.location.origin}/${video.thumbnailUrl}`;
            
        tr.innerHTML = `
            <td><img src="${thumbnailSrc}" alt="thumbnail" width="100"></td>
            <td>${video.title}</td>
            <td>${video.description}</td>
            <td>${video.positiveVotes} / ${video.totalVotes}</td>
            <td>${start + index + 1}</td>
            <td>${(video.rankScore * 100).toFixed(1)}%</td>
        `;
        tbody.appendChild(tr);
    });

    window.scrollTo({ top: 0, behavior: "smooth" }); 
}

function renderPagination() {
    const pagination = document.getElementById("pagination");
    pagination.className = "stranice";
    pagination.innerHTML = "";

    const totalPages = Math.ceil(videos.length / perPage);

    for (let i = 1; i <= totalPages; i++) {
        const span = document.createElement("span");
        span.textContent = i;
        span.className = (i === currentPage) ? "trenutna" : "";
        span.addEventListener("click", () => {
            currentPage = i;
            renderPage(currentPage);
            renderPagination();
        });
        pagination.appendChild(span);
    }

    if (currentPage < totalPages) {
        const next = document.createElement("span");
        next.innerHTML = "&raquo;";
        next.addEventListener("click", () => {
            currentPage++;
            renderPage(currentPage);
            renderPagination();
        });
        pagination.appendChild(next);
    }
}