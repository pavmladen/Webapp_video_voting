window.addEventListener('DOMContentLoaded', () => {
    fetch('/api/videos/random')
          .then(res => res.json())
        .then(videos => {
            const container = document.getElementById('video-container');
            container.innerHTML = '';

            videos.forEach(video => {
                const card = document.createElement('div');
                card.className = 'player-kontejner';
                card.dataset.videoId = video.id;


                card.innerHTML = `
                    <h3>${video.title}</h3>
                    <iframe width="580" height="225" src="https://www.youtube.com/embed/${video.youtubeId}" 
                        title="YouTube video" frameborder="0" allowfullscreen></iframe>
                    <p>${video.description}</p>
                    <div class="vote-buttons">
                        <button onclick="vote(${video.id}, true)">Vote</button>
                    </div>


                `;

                container.appendChild(card);
            });
        })
        .catch(err => console.error('Failed to load videos:', err));
});

document.addEventListener('DOMContentLoaded', () => {
    const refreshBtn = document.getElementById('refresh-button');
    if (refreshBtn) {
        refreshBtn.addEventListener('click', () => {
            window.location.reload();
        });
    }

    const shareBtn = document.getElementById('share-button');
    if (shareBtn) {
        shareBtn.addEventListener('click', () => {
            const videos = document.querySelectorAll('.player-kontejner iframe');
            const links = Array.from(videos).map(iframe =>
                iframe.src.replace('embed/', 'watch?v=')
            );
            const textToCopy = links.join('\n');

            navigator.clipboard.writeText(textToCopy).then(() => {
                alert('Video links copied to clipboard!');
            }).catch(err => {
                alert('Failed to copy links');
                console.error(err);
            });
        });
    }

});


function vote(videoId, isPositive) {
    const videoElements = document.querySelectorAll('.player-kontejner');
    const otherId = [...videoElements]
        .map(el => el.dataset.videoId)
        .find(id => Number(id) !== videoId);

    fetch('/api/vote', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            videoId: videoId,
            positive: isPositive,
            otherVideoId: Number(otherId)
        })
    })
    .then(res => {
        if (res.ok) {
            window.location.reload();
        } else {
            alert('Failed to submit vote');
        }
    });
}